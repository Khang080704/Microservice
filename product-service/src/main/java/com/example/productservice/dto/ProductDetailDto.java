package com.example.productservice.dto;

import com.example.productservice.model.Product;
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
public class ProductDetailDto {
    private ProductDto product;
    private List<ProductDetail> productDetailList;
}
