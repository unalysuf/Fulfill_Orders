package com.example.payroll.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.payroll.modelAssembler.OrderModelAssembler;
import com.example.payroll.models.Order;
import com.example.payroll.notFoundException.OrderNotFoundException;
import com.example.payroll.repositories.OrderRepository;
import com.example.payroll.status.Status;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	private final OrderModelAssembler orderModelAssembler;
	
	public OrderController(OrderRepository orderRepository, OrderModelAssembler orderModelAssembler ){
		this.orderRepository = orderRepository;
		this.orderModelAssembler = orderModelAssembler;
	}
	
	@GetMapping("/orders")
	public CollectionModel<EntityModel<Order>> all(){
		
		List<EntityModel<Order>> orders = orderRepository.findAll().stream()
				.map(orderModelAssembler :: toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(orders, 
				linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	@GetMapping("/orders/{id}")
	public EntityModel<Order> one(@PathVariable Long id){
		
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
		
		return orderModelAssembler.toModel(order);
	}
	
	@PostMapping("/orders")
	public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order){
		
		order.setStatus(Status.IN_PROGRESS);
		Order newOrder = orderRepository.save(order);
		
		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
				.body(orderModelAssembler.toModel(newOrder));
	}
	
	@DeleteMapping("/orders/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Long id){
		
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
		
		if(order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method Not Allowed")
						.withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
	}
	
	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Long id) {

	     Order order = orderRepository.findById(id) //
	    		 .orElseThrow(() -> new OrderNotFoundException(id));

	    if (order.getStatus() == Status.IN_PROGRESS) {
	    	order.setStatus(Status.COMPLETED);
	    	return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
	  }

	    return ResponseEntity //
	    		.status(HttpStatus.METHOD_NOT_ALLOWED) //
	    		.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
	    		.body(Problem.create() //
	    				.withTitle("Method not allowed") //
	    				.withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
	}
	
	
		
	}
