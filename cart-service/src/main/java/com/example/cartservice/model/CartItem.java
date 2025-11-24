package com.example.cartservice.model;

import com.example.cartservice.dto.CartProduct;
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

    private String colorId;
    private String sizeId;

    public void map(ProductResponse productResponse, String colorId, String sizeId, int quantity) {
        this.productId = productResponse.getProduct().getProductId();
        this.price = productResponse.getProduct().getPrice();
        this.productName = productResponse.getProduct().getProductName();

        this.colorId = colorId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }
}
