package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.feignclient.UserClient;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private final UserClient userClient;
    @Autowired
    public OrderService(OrderRepository orderRepository,  UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public OrderDto getOrderByOrderId(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        UserDto userDto = userClient.getUserById(order.getUserId());
        return new OrderDto(order.getId(), order.getProduct(), order.getPrice(), userDto);
    }
}
