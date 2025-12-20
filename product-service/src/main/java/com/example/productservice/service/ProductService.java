package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public PagedModel<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        products.map(product -> ProductDto.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .productName(product.getProductName())
                .description(product.getDescription())
                .categoryName(product.getCategory().getCategoryName())
                .brandName(product.getBrand().getName())
                .build());

        return new PagedModel<>(products);
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
