package com.example.cartservice.model;

import com.example.cartservice.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private String productId;
    private int quantity;

    private String productName;
    private double price;

    public void map(ProductResponse productResponse, int quantity) {
        this.productId = productResponse.getProductId();
        this.productName = productResponse.getProductName();
        this.price = productResponse.getPrice();
        this.quantity = quantity;
    }

}
