package com.example.payroll.loadData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.payroll.models.Employee;
import com.example.payroll.models.Order;
import com.example.payroll.repositories.EmployeeRepository;
import com.example.payroll.repositories.OrderRepository;
import com.example.payroll.status.Status;

@Configuration
public class LoadDatabase {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
		
		return args -> {
		      employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
		      employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

		      employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

		      
		      orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
		      orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

		      orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));

		};
	}
}
