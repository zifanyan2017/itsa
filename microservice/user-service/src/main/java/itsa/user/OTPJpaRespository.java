package itsa.user;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OTPJpaRespository extends JpaRepository<OTP, Integer> {

    @Modifying
    @Query(value = "insert into otp (username, verificationcode, expirydate, phonenumber) values (:username, :verificationcode, :expirydate, :phonenumber)",
            nativeQuery = true)
    void insertOTP(@Param("username") String username, @Param("verificationcode") String verificationcode, @Param("expirydate") Timestamp expirydate, @Param("phonenumber") String phonenumber);

    @Query(
            value = "SELECT * FROM otp where username = :username order by id DESC LIMIT 1",
            nativeQuery = true)
    List<OTP> findOTP(@Param("username") String username);
}
