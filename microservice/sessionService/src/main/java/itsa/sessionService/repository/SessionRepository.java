package itsa.sessionService.repository;

import itsa.sessionService.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query(
            value = "SELECT sessionID FROM Session where sessionID= :sessionID",
            nativeQuery = true)
    String getSessionIDBySessionID( @Param("sessionID") String sessionID );

    @Query(
            value = "SELECT state FROM Session where sessionID= :sessionID",
            nativeQuery = true)
    String getStateBySessionID( @Param("sessionID") String sessionID );

    @Query(
            value = "SELECT sessionID FROM Session where userID= :userID",
            nativeQuery = true)
    String findSessionIDbyUserID( @Param("userID") int userID );

    @Modifying
    @Query(value = "INSERT into Session ( sessionID, userID ) values ( :sessionID, :userID )",
            nativeQuery = true)
    void newSession( @Param("sessionID") String sessionID, @Param("userID") int userID );

    @Modifying
    @Query(value = "UPDATE Session SET sessionId = :sessionId WHERE userID = :userID",
            nativeQuery = true)
    void updateSession( @Param("sessionId") String sessionId, @Param("userID") int userID );

    @Modifying
    @Query(value = "UPDATE Session SET state = :state WHERE sessionId = :sessionId AND userID = :userID",
            nativeQuery = true)
    void updateState( @Param("state") String state ,@Param("sessionId") String sessionId, @Param("userID") int userID );


//    @Query(
//            value = "SELECT ID,user_id,Fullname,Address,Position,date_of_hiring, Salary,entitled_leave from Employee;",
//            nativeQuery = true)
//    List<Session> findAllEmployee();
//
//    @Query(
//            value = "SELECT * FROM Session where userID = :userID",
//            nativeQuery = true)
//    List<Session> findEmp(@Param("userID") int userID);
//
//    @Query(
//            value = "SELECT ID,user_id FROM Employee where user_id= :userId",
//            nativeQuery = true)
//    int findEmpId(@Param("userId") int userId);
//
//    @Modifying
//    @Query(value = "insert into Employee (Fullname,user_id,Address,Position,date_of_hiring,Salary,entitled_leave) " +
//            "values (:fullname,:userId,:address,:position,:hireDate,:salary,:entitled_leave)",
//            nativeQuery = true)
//    void insertEmployee(@Param("fullname") String fullname,@Param("userId") int user_id, @Param("address") String address,
//                        @Param("position") String position, @Param("hireDate") String hireDate,
//                        @Param("salary") Double salary, @Param("entitled_leave") Double leave);
////ID,Fullname,Address,Email,Position,DateOfHiring,Salary,entitled_leave
//    @Modifying
//    @Query(value = "update Employee set fullname = :fullname,Address=:address,Position=:position,date_of_hiring=:hireDate," +
//            "Salary=:salary,entitled_leave=:entitled_leave where id = :id ;",
//            nativeQuery = true)
//    void updateEmployee(@Param("id") int id,@Param("fullname") String fullname, @Param("address") String address,
//                         @Param("position") String position, @Param("hireDate") String hireDate,
//                        @Param("salary") Double salary, @Param("entitled_leave") Double leave );
//
//    @Modifying
//    @Query(value = "DELETE FROM Employee WHERE (id=:id) ;",
//            nativeQuery = true)
//    void deleteEmployee(@Param("id") int id);


}