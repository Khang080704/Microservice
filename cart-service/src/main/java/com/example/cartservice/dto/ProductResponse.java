package com.example.cartservice.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String productId;
    private String productName;
    private String categoryName;
    private String brandName;
    private Double price;
    private String description;
}
