package itsa.salaryservice.resources;

import itsa.salaryservice.models.Salary;
import itsa.salaryservice.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salary")
public class SalaryResource {
    @Autowired
    SalaryRepository salaryRepository;

    @GetMapping("/all")
    public List<Salary> getAll() {
        return salaryRepository.retrieveAll();
    }


    @RequestMapping("/{userId}&{year}&{month}")
    public Salary getAllSalaryById(@PathVariable("userId") String userId,
                                   @PathVariable("year") int year,
                                   @PathVariable("month") int month) {
        return salaryRepository.getAllById(userId, year, month);
    }
}
