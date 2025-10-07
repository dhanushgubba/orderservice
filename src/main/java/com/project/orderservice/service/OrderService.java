package com.project.orderservice.service;

import java.util.List;

import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderStatus;

public interface OrderService {

	Order placeOrder(Order order);
	//Get order by ID
	Order getOrderById(Long orderId);
	List<Order> getOrdersByUserId(Long userId);
	List<Order> getAllOrders();
	void deleteOrder(Long orderId);
	Order updateOrderStatus(Long orderId, OrderStatus status);
}
