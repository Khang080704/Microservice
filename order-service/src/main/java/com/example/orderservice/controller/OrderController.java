package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok().body(Map.of("message", "Order created"));
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<Order> result = orderService.getAllOrder();
        return ResponseEntity.ok().body(result);
    }
}
