package com.example.fulfillorder.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String role;
	
	public Employee() {}
	
	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) {
			return true;
		}
		if(!(o instanceof Employee)) {
			return false;
		}
		
		Employee employee = (Employee) o;
		
		return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name) 
				&& Objects.equals(this.role, employee.role);	
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id,this.name,this.role);
	}
	
	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
	}
	
}
