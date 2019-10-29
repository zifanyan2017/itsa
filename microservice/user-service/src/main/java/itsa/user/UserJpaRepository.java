package itsa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserJpaRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query(value = "insert into user (username, role, password, phonenumber, email, salt) values (:username, :role, :password, :phonenumber, :email, :salt)",
            nativeQuery = true)
    void insertUser(@Param("username") String username, @Param("role") String role, @Param("password") String password, @Param("phonenumber") String phonenumber, @Param("email") String email, @Param("salt") byte[] salt);

    @Query(
            value = "SELECT * FROM user ORDER BY id DESC",
            nativeQuery = true)
    List<User> getAllUsers();

    @Query(
            value = "SELECT email FROM user WHERE id = :id",
            nativeQuery = true)
    String getEmailById(@Param("id") int id);

    @Query(
            value = "SELECT * FROM user where username = :username",
            nativeQuery = true)
    List<User> findUser(@Param("username") String username);

    @Query(
            value = "SELECT * FROM user where username = :username or email = :email or phonenumber = :phonenumber",
            nativeQuery = true)
    List<User> findUser(@Param("username") String username, @Param("email") String email, @Param("phonenumber") String phonenumber);

    @Modifying
    @Query(
            value = "UPDATE user set email = :email, phonenumber = :phonenumber, role = :role WHERE username = :username",
            nativeQuery = true)
    void updateUser(@Param("email") String email, @Param("phonenumber") String phonenumber, @Param("role") String role, @Param("username") String username);

    @Modifying
    @Query(
            value = "UPDATE user SET password = :password WHERE username = :username",
            nativeQuery = true)
    void updatePassword(@Param("password") String password, @Param("username") String username);

    @Modifying
    @Query(
            value = "DELETE FROM user WHERE username = :username",
            nativeQuery = true)
    void deleteUser(@Param("username") String username);
}
