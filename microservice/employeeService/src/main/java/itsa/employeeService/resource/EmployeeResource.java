package itsa.employeeService.resource;

import itsa.employeeService.model.Employee;
import itsa.employeeService.repository.EmployeeRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class EmployeeResource {
    @Autowired
    EmployeeRepository usersRepository;
    @GetMapping(value = "/all")
    public List<Employee> getAll() {
        return usersRepository.findAllEmployee();
    }

    @GetMapping(value = "/find/{id}")
    public List<Employee> find(@PathVariable("id") int id) {
        return usersRepository.findEmp(id);
    }

    @GetMapping(value = "/findempid/{id}")
    public JSONArray findEmpId(@PathVariable("id") int id){
        JSONObject json = new JSONObject();
        json.put("ID", id);
        json.put("user_id", usersRepository.findEmpId(id));
        JSONArray array = new JSONArray();
        array.add(json);

        return array;
    }

    @PostMapping(value="/add")
    @Transactional
    public void add(@Param("fullname") String fullname,@Param("userId") int userId, @Param("address") String address
                    , @Param("position") String position, @Param("hireDate") String hireDate,
                    @Param("salary") Double salary, @Param("entitled_leave") Double leave)
    { usersRepository.insertEmployee(fullname,userId,address,position,hireDate,salary,leave);}

    @PutMapping(value="/update")
    @Transactional
    public void update(@Param("id") int id,@Param("fullname") String fullname, @Param("address") String address,
                       @Param("position") String position, @Param("hireDate") String hireDate,
                       @Param("salary") Double salary, @Param("entitled_leave") Double leave)
    { usersRepository.updateEmployee(id,fullname,address,position,hireDate,salary,leave);}

    @DeleteMapping(value="/delete")
    @Transactional
    public void delete(@RequestParam("id") int id)
    { usersRepository.deleteEmployee(id);}
}
