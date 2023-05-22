package com.example.payroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payroll.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
