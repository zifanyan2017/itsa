package itsa.sessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionServiceApplicationTests {
	@Autowired
	//EmployeeRepository usersRepository;
            itsa.sessionService.resource.SessionResource er = new itsa.sessionService.resource.SessionResource();



//	@Test
//    public void testeuqal(){
//	    Employee a = new Employee(11,"test",4);
//        Employee b = new Employee(11,"test",4);
//        assertEquals(a,b);
//    }


//	@Test
//    @Transactional
//	public void testInsert(){
//
//        er.add(999,"test");
//		ArrayList<Employee> test = (ArrayList<Employee>)er.find(999);
//	}
//
//	@Test
//	public void testSelect(){
//
//	}
//
//	@Test
//	@Transactional
//	public void testUpdate(){
//
//	}
//
//	@Test
//	@Transactional
//	public void testDelete(){
//
//	}


    @Test
	public void contextLoads() {
	}

}
