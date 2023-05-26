package com.example.payroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payroll.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
