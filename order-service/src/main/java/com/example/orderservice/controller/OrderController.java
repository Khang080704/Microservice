package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.feignclient.UserClient;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok().body(Map.of("message", "Order created" + created.toString()));
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<Order> result = orderService.getAllOrder();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("{order_id}")
    public ResponseEntity getOrderById(@PathVariable Long order_id) {
        OrderDto result = orderService.getOrderByOrderId(order_id);
        return ResponseEntity.ok().body(result);
    }
}
