package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = ProductDto.builder()
                    .productId(product.getId())
                    .productName(product.getProductName())
                    .categoryName(product.getCategory().getCategoryName())
                    .brandName(product.getBrand().getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public ProductDto getProductDetail(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            Product currentProduct = product.get();

            return ProductDto.builder()
                    .productId(currentProduct.getId())
                    .productName(currentProduct.getProductName())
                    .brandName(currentProduct.getBrand().getName())
                    .categoryName(currentProduct.getCategory().getCategoryName())
                    .description(currentProduct.getDescription())
                    .price(currentProduct.getPrice())
                    .build();
        }
        else {
            return null;
        }
    }
}
