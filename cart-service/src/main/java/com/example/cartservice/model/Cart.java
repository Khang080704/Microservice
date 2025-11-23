package com.example.cartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    private String id;
    private String userId;

    private List<CartItem> items;

    private int totalQuantity;
    private double totalPrice;

    public double calculateTotalPrice() {
        for (CartItem item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
    public int calculateTotalQuantity() {
        for (CartItem item : items) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

}
