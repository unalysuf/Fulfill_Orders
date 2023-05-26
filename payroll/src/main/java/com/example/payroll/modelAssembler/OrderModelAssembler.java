package com.example.payroll.modelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.payroll.controller.OrderController;
import com.example.payroll.models.Employee;
import com.example.payroll.models.Order;
import com.example.payroll.status.Status;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order order) {
		
		//Unconditional Links
		EntityModel<Order> orderModel = EntityModel.of(order,
				linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).all()).withRel("orders"));
		
		//Conditional Links
		if(order.getStatus() == Status.IN_PROGRESS) {
		      orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
		      orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
		}
		
		return orderModel;
	}

}
