package com.example.productservice.controller;

import com.example.productservice.dto.ProductDetailDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getProductsByCategory(@RequestParam String category) {
        List<Product> result = categoryService.getAllProductFromCategory(category);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> getProductDetailById(@PathVariable("product_id") String product_id) {
        ProductDto result = productService.getProductDetail(product_id);
        return ResponseEntity.ok(result);
    }
}
