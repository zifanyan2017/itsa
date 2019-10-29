package itsa.leaveService.leave.repository;



import itsa.leaveService.leave.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface LeaveRepository extends JpaRepository<Leave,Integer>{
    @Query(value = "select * from itsa_leave.Leave where emp_id = :emp_id", nativeQuery = true)
    List<Leave> findAllByUserId(String emp_id);

    @Query(value = "select * from itsa_leave.Leave", nativeQuery = true)
    List<Leave> findAllLeave();

    @Modifying
    @Query(value = "update itsa_leave.Leave set start_date = :start_date, end_date = :end_date where id = :id",nativeQuery = true)
    void updateLeave(@Param("start_date") java.sql.Date start_date, @Param("end_date") java.sql.Date end_date, @Param("id") String id);

    @Modifying
    @Query(value = "update itsa_leave.Leave set status = :status where id = :id",nativeQuery = true)
    void approveLeave( @Param("status") String status, @Param("id") String id);



    @Modifying
    @Query(value = "insert itsa_leave.Leave set start_date = :start_date ,end_date = :end_date, emp_id = :emp_id",nativeQuery = true)
    void insertLeave(@Param("start_date") java.sql.Date start_date, @Param("end_date") java.sql.Date end_date, @Param("emp_id") String emp_id);

}
