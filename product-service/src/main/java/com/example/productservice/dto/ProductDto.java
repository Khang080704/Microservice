package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String productId;
    private String productName;
    private String categoryName;
    private String brandName;
    private Double price;
    private String description;
}
