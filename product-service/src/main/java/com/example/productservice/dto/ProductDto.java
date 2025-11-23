package com.example.productservice.dto;

import com.example.productservice.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String productId;
    private String productName;
    private String categoryName;
    private String brandName;
    private int stock;
    private Double price;

}
