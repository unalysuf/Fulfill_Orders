package com.example.payroll.loadData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.payroll.models.Employee;
import com.example.payroll.repositories.EmployeeRepository;

@Configuration
public class LoadDatabase {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		
		return args -> {
			log.info("Preloading "+repository.save(new Employee("Johny", "Depp", "Engineer")));
			log.info("Preloading "+repository.save(new Employee("Angelina", "Jolie", "Accounter")));		
		};
	}

}
