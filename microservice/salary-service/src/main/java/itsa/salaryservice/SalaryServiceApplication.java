package itsa.salaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "itsa.salaryservice.repository")
@SpringBootApplication
public class SalaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryServiceApplication.class, args);
	}

}
