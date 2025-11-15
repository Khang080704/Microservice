package com.example.productservice.service;

import com.example.productservice.dto.ProductDetailDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductDetail;
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
                    .build();
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public ProductDetailDto getProductDetail(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            Product currentProduct =  product.get();
            ProductDetailDto productDetailDto = new ProductDetailDto();

            productDetailDto.setProductDetailList(currentProduct.getDetails());
            productDetailDto.setProduct(ProductDto.builder().productId(currentProduct.getId())
                            .productName(currentProduct.getProductName())
                            .brandName(currentProduct.getBrand().getName())
                            .categoryName(currentProduct.getCategory().getCategoryName())
                            .build());
            return productDetailDto;
        }
        else {
            return null;
        }
    }
}
