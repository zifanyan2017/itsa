package itsa.employeeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import itsa.employeeService.model.Employee;
import itsa.employeeService.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    private static int testid=2;

    @Test
    @Order(1)
    void testValidInsert() throws Exception{
        boolean count = false;
        mockMvc.perform(post("/add").param("userId","999").param("fullname","123123123").param("address","test2")
                .param("position","test3").param("hireDate","2019-02-25").param("salary","4")
                .param("leave","4"))
                .andExpect(status().isOk());
        
        ArrayList<Employee> tmpList = (ArrayList<Employee>)employeeRepository.findAllEmployee();
        for (int i = 0; i < tmpList.size(); i++) {

            if(tmpList.get(i).getFullName().equals("123123123")){

                testid = tmpList.get(i).getId();
                System.out.println(testid);
                System.out.println(Integer.toString(testid));
                count = true;
            }
        }
        Assert.assertEquals(true,count);
//        Assert.assertEquals(tmpList.get(0).getId(),999);
//        Assert.assertEquals(tmpList.get(0).getFullName(),"test");
//        Assert.assertEquals(tmpList.get(0).getAddress(),"test2");
//        Assert.assertEquals(tmpList.get(0).getPosition(),"test3");
//        Assert.assertEquals(tmpList.get(0).getHireDate(),"2019-02-25");
//        Assert.assertTrue("Not equals", (double)tmpList.get(0).getLeave() -  4.0 == 0);
//        Assert.assertTrue("Not equals", (double)tmpList.get(0).getSalary() -  4.0 == 0);
    }





    @Test
    @Order(2)
    void testUpdate() throws Exception{
        System.out.println("testupdate:"+Integer.toString(testid));
        mockMvc.perform(put("/update").param("id",Integer.toString(testid)).param("fullname","test3")
                .param("address","test4").param("position","test5").param("hireDate","2019-03-25")
                .param("salary","3").param("leave","3"))
                .andExpect(status().isOk());
        ArrayList<Employee> tmpList = (ArrayList<Employee>)employeeRepository.findEmp(testid);

        Assert.assertEquals(tmpList.get(0).getId(),testid);
        Assert.assertEquals(tmpList.get(0).getFullName(),"test3");
        Assert.assertEquals(tmpList.get(0).getAddress(),"test4");
        Assert.assertEquals(tmpList.get(0).getPosition(),"test5");
        Assert.assertEquals(tmpList.get(0).getHireDate(),"2019-03-25");
        Assert.assertTrue("Not equals", (double)tmpList.get(0).getLeave() -  3.0 == 0);
        Assert.assertTrue("Not equals", (double)tmpList.get(0).getSalary() -  3.0 == 0);
    }

    @Test
    @Order(3)
    void testSelect() throws Exception{
        mockMvc.perform(get("/find/"+Integer.toString(testid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is("test3")));
                //.andExpect(content().json("[{\"id\":999,\"fullName\":\"test2\",\"leave\":4}]"));
        //ArrayList<Employee> tmpList = (ArrayList<Employee>)employeeRepository.findEmp(999);
    }

    @Test
    @Order(4)
    void testValidDelete() throws Exception{
        mockMvc.perform(delete("/delete").param("id",Integer.toString(testid)))
                .andExpect(status().isOk());
        ArrayList<Employee> tmpList = (ArrayList<Employee>)employeeRepository.findEmp(999);
        assertThat(tmpList,is(empty()));
    }



}
