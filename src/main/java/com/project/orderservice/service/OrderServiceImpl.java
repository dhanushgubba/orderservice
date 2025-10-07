package com.project.orderservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderItem;
import com.project.orderservice.model.OrderStatus;
import com.project.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	private RestTemplate restTemplate;
	@Override
	public Order placeOrder(Order order) {
		// TODO Auto-generated method stub
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(OrderStatus.NEW);
		if(order.getItems()!=null) {
			for(OrderItem item : order.getItems()) {
				item.setOrder(order);
			}
		}
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		Optional<Order> order = orderRepository.findById(orderId);
		return order.orElse(null);
	}

	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserId(userId);
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(orderId);
	}

	@Override
	public Order updateOrderStatus(Long orderId, OrderStatus status) {
	    Optional<Order> optionalOrder = orderRepository.findById(orderId);
	    if(optionalOrder.isPresent()) {
	    	Order order = optionalOrder.get();
	    	order.setStatus(status);
	    	return orderRepository.save(order);
	    }
	    return null;
	}
}
