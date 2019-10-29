//package itsa.employeeService;
//
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TomcatConnectionPoolTest {
//    @Autowired
//    private DataSource dataSource;
//
//    @Test
//    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
//        assertThat(dataSource.getClass().getName())
//                .isEqualTo("org.apache.tomcat.jdbc.pool.DataSource");
//    }
//}
