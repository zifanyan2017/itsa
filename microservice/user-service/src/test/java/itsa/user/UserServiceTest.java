package itsa.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private MockMvc movkMVC;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    OTPJpaRespository otpJpaRespository;

    // Test Valid Registration
    @Test
    @Order(1)
    void testRegister() throws Exception{
        movkMVC.perform(post("/register")
                .param("username","test")
                .param("role","test")
                .param("password","test")
                .param("phonenumber","14248887874")
                .param("email","fongkinshing@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));
    }

    // Test Invalid Registration (Duplicate)
    @Test
    @Order(2)
    void testRegisterDuplicate() throws Exception{
        movkMVC.perform(post("/register")
                .param("username","test")
                .param("role","test")
                .param("password","test")
                .param("phonenumber","14248887874")
                .param("email","fongkinshing@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));
    }

    // Test Find by Username
    @Test
    @Order(3)
    void testFind() throws Exception{
        movkMVC.perform(get("/find/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("test")));

    }

    // Test get all users
    @Test
    @Order(4)
    void testFindAll() throws Exception{
        System.out.print(movkMVC.perform(post("/getall")));
        movkMVC.perform(get("/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[-1].username", is("test")));
    }

    // Test Valid Login
    @Test
    @Order(5)
    void testLogin() throws Exception{
        movkMVC.perform(post("/login")
                .param("username", "test")
                .param("password", "test")
                .param("testmode", "yes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));
    }
    // Test InValid OTP
    @Test
    @Order(6)
    void testInvalid2fa() throws Exception{
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date added6minutes =new Date(t);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = dateFormat.format(added6minutes);
        movkMVC.perform(post("/2fa")
                .param("username", "test")
                .param("verificationcode","12356642")
                .param("datetime", strDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));
    }

    // Test Expired OTP
    /*@Test
    @Order(6)
    void testExpired2fa() throws Exception{

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date added6minutes =new Date(t + (60000 * 60000));
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = dateFormat.format(added6minutes);

        movkMVC.perform(post("/2fa")
                .param("username", "test")
                .param("verificationcode","1235664")
                .param("datetime", strDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));
    }*/
    // Test Valid OTP
    @Test
    @Order(7)
    void test2fa() throws Exception{
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date added6minutes =new Date(t);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = dateFormat.format(added6minutes);
        movkMVC.perform(post("/2fa")
                .param("username", "test")
                .param("verificationcode","1235664")
                .param("datetime", strDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));
    }

    // Test InValid Username Login
    @Test
    @Order(8)
    void testInvalidUsernameLogin() throws Exception{
        movkMVC.perform(post("/login")
                .param("username", "test2")
                .param("password", "test")
                .param("testmode", "yes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));
    }

    // Test InValid Password Login
    @Test
    @Order(9)
    void testInvalidPasswordLogin() throws Exception{
        movkMVC.perform(post("/login")
                .param("username", "test")
                .param("password", "test2")
                .param("testmode", "yes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));
    }

    // Test Valid Update
    @Test
    @Order(10)
    void testUpdate() throws Exception{
        movkMVC.perform(post("/update")
                .param("email","fongkinshing@hotmail.com")
                .param("phonenumber","6581338105")
                .param("role","test2")
                .param("username","test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));

    }

    // Test InValid Update
    @Test
    @Order(10)
    void testInvalidUpdate() throws Exception{
        movkMVC.perform(post("/update")
                .param("email","fongkinshing@hotmail.com")
                .param("phonenumber","6581338105")
                .param("role","test2")
                .param("username","test2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));

    }

    // Test Valid Change Password
    @Test
    @Order(11)
    void testChangePassword() throws Exception{
        movkMVC.perform(post("/changepassword")
                .param("username","test")
                .param("oldpassword","test")
                .param("password","test2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));

    }

    // Test Invalid Username Change Password
    @Test
    @Order(12)
    void testInvalidUsernameChangePassword() throws Exception{
        movkMVC.perform(post("/changepassword")
                .param("username","test2")
                .param("oldpassword","test2")
                .param("password","test3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));

    }

    // Test Invalid Old Password Change Password
    @Test
    @Order(13)
    void testInvalidPasswordChangePassword() throws Exception{
        movkMVC.perform(post("/changepassword")
                .param("username","test")
                .param("oldpassword","test")
                .param("password","test2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("fail")));

    }

    // Test Valid Delete
    @Test
    @Order(14)
    void testDelete() throws Exception{
        movkMVC.perform(post("/delete")
                .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status",is("success")));
    }


}
