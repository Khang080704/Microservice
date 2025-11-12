package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.event.OrderPlacedEvent;
import com.example.orderservice.feignclient.UserClient;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private final UserClient userClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository,  UserClient userClient,  KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.userClient = userClient;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        //Send event to kafka
        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .Total(savedOrder.getPrice())
                .build();
        kafkaTemplate.send("order-topic", orderPlacedEvent);
        System.out.println("Event sent to topic");
        return savedOrder;
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
