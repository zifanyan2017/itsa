package itsa.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@RestController
public class UserServiceController {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    OTPJpaRespository otpJpaRespository;

    final OTP otp = new OTP();


    @RequestMapping(value="/login", method= RequestMethod.POST)
    @Transactional
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="testmode") String testmode) {
        List<User> users = userJpaRepository.findUser(username);
        JSONObject json = new JSONObject();
        if (users.size() > 0) {

            for (User user : users) {

                if (!getSecurePassword(password, user.getSalt()).equals(user.getEncryptedPassword())){
                    json.put("status", "fail");
                    json.put("error", "Invalid username/password");

                } else {
                    json.put("status", "success");
                    JSONArray array = new JSONArray();
                    JSONObject item = new JSONObject();
                    item.put("id", user.getId());
                    item.put("username", user.getUsername());
                    item.put("phonenumber", user.getPhoneNumber());
                    array.add(item);

                    json.put("user", array);


                    LocalDateTime fiveMinutesLater = LocalDateTime.now(ZoneId.of("Asia/Singapore")).plusMinutes(5);

                    if (!testmode.equals("yes")) {
                        otpJpaRespository.insertOTP(username, user.generateVerificationCode(), Timestamp.valueOf(fiveMinutesLater), user.getPhoneNumber());
                        otp.sendOTP(user.getPhoneNumber(), user.getVerificationCode());

                    } else{
                        otpJpaRespository.insertOTP(username, user.generateTestVC(), Timestamp.valueOf(fiveMinutesLater), user.getPhoneNumber());
                    }
                }
            }

        } else {
            json.put("status", "fail");
            json.put("error", "Invalid username/password");
        }
        return json.toString();

    }
    @RequestMapping(value="/register", method= RequestMethod.POST)
    @Transactional
    public String register(@RequestParam(value="username") String username,@RequestParam(value="role") String role, @RequestParam(value="password") String password, @RequestParam(value="phonenumber") String phoneNumber, @RequestParam(value="email") String email) throws NoSuchProviderException, NoSuchAlgorithmException {
        JSONObject json = new JSONObject();
        List<User> users = userJpaRepository.findUser(username,email,phoneNumber);
        if (users.size() > 0) {
            json.put("status", "fail");
            json.put("error", "username/ email/ phonenumber already existed");

        } else {
            byte[] salt = getSalt();
            password = getSecurePassword(password, salt);
            userJpaRepository.insertUser(username, role, password, phoneNumber, email, salt);

            json.put("status", "success");

        }
        return json.toString();
    }

    @RequestMapping(value="/2fa", method= RequestMethod.POST)
    @Transactional
    public String login2fa(@RequestParam(value="username") String username, @RequestParam(value="verificationcode") String verificationCode, @RequestParam(value="datetime") String datetime){
        List<OTP> otps = otpJpaRespository.findOTP(username);
        JSONObject json = new JSONObject();
        if (otps.size() > 0) {
            for (OTP otp : otps){
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

                    Date parsedDate = dateFormat.parse(datetime);

                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    if (otp.getVerificationCode().equals(verificationCode) && timestamp.before(otp.getExpiryDate())){
                        json.put("status", "success");
                    } else {
                        json.put("status", "fail");
                        json.put("error", "invalid otp");
                    }
                } catch(Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                    json.put("status", "fail");
                    json.put("error", "Error in timestamp");
                }

            }
        } else {
            json.put("status", "fail");
            json.put("error", "Invalid username/password");
        }
        return json.toString();
    }

    @RequestMapping(value="/findEmailById/{id}", method= RequestMethod.GET)
    public String findEmailById(@PathVariable("id") int id){
        JSONObject json = new JSONObject();
        String email = userJpaRepository.getEmailById(id);
        if (email != null) {
            json.put("status", "success");
            json.put("email", email);
        } else {
            json.put("status", "fail");
            json.put("error", "Invalid id");
        }
        return json.toString();
    }

    @RequestMapping(value="/find/{username}", method= RequestMethod.GET)
    public List<User> find(@PathVariable("username") String username){
        return userJpaRepository.findUser(username);
    }

    @RequestMapping(value="/getall", method= RequestMethod.GET)
    public List<User> find(){
        return userJpaRepository.getAllUsers();
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    @Transactional
    public String update( @RequestParam(value="email") String email, @RequestParam(value="phonenumber") String phonenumber, @RequestParam(value="role") String role,  @RequestParam(value="username") String username){
        JSONObject json = new JSONObject();
        userJpaRepository.updateUser(email,phonenumber,role,username);
        List<User> users = userJpaRepository.findUser(username);
        if (users.size() > 0){
            for (User user : users){
                if (user.getEmail().equals(email) && user.getPhoneNumber().equals(phonenumber) && user.getRole().equals(role)){
                    json.put("status", "success");
                }
                else{
                    json.put("status", "fail");
                }
            }
        }
        else{
            json.put("status", "fail");
        }


        return json.toString();
    }

    @RequestMapping(value="/changepassword", method= RequestMethod.POST)
    @Transactional
    public String changePassword(@RequestParam(value="username") String username, @RequestParam(value="oldpassword") String oldPassword, @RequestParam(value = "password") String password){
        List<User> users = userJpaRepository.findUser(username);
        JSONObject json = new JSONObject();
        String newPassword = "";
        if (users.size() > 0) {
            for (User user : users) {
                if (!getSecurePassword(oldPassword, user.getSalt()).equals(user.getEncryptedPassword())){
                    json.put("status", "fail");
                    json.put("error", "Invalid username/password");

                } else {
                    newPassword = getSecurePassword(password, user.getSalt());
                    userJpaRepository.updatePassword(newPassword, username);
                    json.put("status", "success");
                }
            }


        } else{
            json.put("status", "fail");
            json.put("error", "Invalid username");
        }
        return json.toString();
    }

    @RequestMapping(value="/delete", method= RequestMethod.POST)
    @Transactional
    public String delete( @RequestParam(value="username") String username){
        userJpaRepository.deleteUser(username);
        JSONObject json = new JSONObject();

        json.put("status", "success");
        return json.toString();
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    /*
    @RequestMapping(value = "/abc", method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }

     */


}
