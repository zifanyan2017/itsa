package itsa.user;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class OTP {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "verificationcode")
    private String verificationCode;

    @Column(name = "expirydate")
    private Timestamp expiryDate;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Transient
    public static final String ACCOUNT_SID = "AC1b21105b34eeeaeca8bd12e1ff1c6284";

    @Transient
    public static final String AUTH_TOKEN = "84e3d6d3999d1166256cc72da31cc0eb";

    @Transient
    public static final String fromPhoneNumber = "+14248887874";

    public void sendOTP(String phoneNumber, String verificationCode){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+"+ phoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                "ITSA - HRMS OTP: " + verificationCode)
                .create();


    }

    public String getLatestMessageSent(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        ResourceSet<Message> messages = Message.reader().limit(1).read();

        for(Message record : messages) {

            return record.getSid().toString();
        }
        return "";
    }

    public String getVerificationCode(){
        return this.verificationCode;
    }

    public Timestamp getExpiryDate(){
        return this.expiryDate;
    }
}
