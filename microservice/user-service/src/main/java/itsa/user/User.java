package itsa.user;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String encryptedPassword;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "salt")
    private byte[] salt;
    @Transient
    private boolean loginStatus = false;

    @Transient
    private static final int MAX_VERIFICATION_CODE = 100000;

    @Transient
    private static final int MIN_VERIFICATION_CODE = 999999;

    @Transient
    private String verificationCode;

    @Transient
    private boolean confirmed = false;

    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    @JsonIgnore
    public String getEncryptedPassword() { return this.encryptedPassword;}

    @JsonIgnore
    public byte[] getSalt() { return this.salt; }
    public String getRole(){
        return this.role;
    }
    public String getEmail() {return this.email; }
    public String getFullname() { return this.fullname; }
    public String getPhoneNumber(){ return this.phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getVerificationCode() { return this.verificationCode; }

    public String generateTestVC(){
        this.verificationCode = "1235664";
        return verificationCode;
    }

    public String generateVerificationCode() {
        Random rand = new Random();
        Integer code = rand.nextInt(MIN_VERIFICATION_CODE
                - MAX_VERIFICATION_CODE + 1) + MAX_VERIFICATION_CODE;
        this.verificationCode = code.toString();
        return code.toString();
    }

    public boolean confirm(final String verificationCode) {
        if (!this.verificationCode.equals(verificationCode)) {
            this.confirmed = false;
        }
        else {
            this.confirmed = true;
        }
        return this.confirmed;
    }



}
