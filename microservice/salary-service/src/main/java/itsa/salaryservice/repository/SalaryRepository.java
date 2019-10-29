package itsa.salaryservice.repository;

import itsa.salaryservice.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {
    @Query(value = "select * from salary where user_id =?1 and year(date_added)=?2 and month(date_added)=?3", nativeQuery = true)
    Salary getAllById(String userId, int year, int month);

    @Query(value = "select * from salary", nativeQuery = true)
    List<Salary> retrieveAll();

}
