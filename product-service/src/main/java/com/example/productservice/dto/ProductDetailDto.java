package com.example.productservice.dto;

import com.example.productservice.model.Color;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductDetail;
import com.example.productservice.model.Size;
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
    private List<Color> colors;
    private List<Size> sizes;
}
