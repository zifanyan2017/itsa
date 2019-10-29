package itsa.employeeService.repository;

import itsa.employeeService.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(
            value = "select ID,user_id,Fullname,Address,Position,date_of_hiring, Salary,entitled_leave from Employee;",
            nativeQuery = true)
    List<Employee> findAllEmployee();

    @Query(
            value = "SELECT * FROM Employee where id = :id",
            nativeQuery = true)
    List<Employee> findEmp(@Param("id") int id);

    @Query(
            value = "SELECT ID,user_id FROM Employee where user_id= :userId",
            nativeQuery = true
    )
    int findEmpId(@Param("userId") int userId);


    @Modifying
    @Query(value = "insert into Employee (Fullname,user_id,Address,Position,date_of_hiring,Salary,entitled_leave) " +
            "values (:fullname,:userId,:address,:position,:hireDate,:salary,:entitled_leave)",
            nativeQuery = true)
    void insertEmployee(@Param("fullname") String fullname,@Param("userId") int user_id, @Param("address") String address,
                        @Param("position") String position, @Param("hireDate") String hireDate,
                        @Param("salary") Double salary, @Param("entitled_leave") Double leave);
//ID,Fullname,Address,Email,Position,DateOfHiring,Salary,entitled_leave
    @Modifying
    @Query(value = "update Employee set fullname = :fullname,Address=:address,Position=:position,date_of_hiring=:hireDate," +
            "Salary=:salary,entitled_leave=:entitled_leave where id = :id ;",
            nativeQuery = true)
    void updateEmployee(@Param("id") int id,@Param("fullname") String fullname, @Param("address") String address,
                         @Param("position") String position, @Param("hireDate") String hireDate,
                        @Param("salary") Double salary, @Param("entitled_leave") Double leave );

    @Modifying
    @Query(value = "DELETE FROM Employee WHERE (id=:id) ;",
            nativeQuery = true)
    void deleteEmployee(@Param("id") int id);


}