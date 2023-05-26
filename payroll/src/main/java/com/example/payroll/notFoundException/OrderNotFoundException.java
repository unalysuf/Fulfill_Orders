package com.example.payroll.notFoundException;

public class OrderNotFoundException extends RuntimeException {
	
		public OrderNotFoundException(Long id) {
			super("Could not find order "+id);
		}
}
