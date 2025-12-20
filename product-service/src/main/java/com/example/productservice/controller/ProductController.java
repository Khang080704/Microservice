package com.example.productservice.controller;

import com.example.productservice.dto.ProductDetailDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
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
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "price") String sortBy
    ) {
        Sort sort = direction.equals("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        PagedModel<Product> products = productService.findAll(pageRequest);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page
    ) {
        PageRequest pageRequest = PageRequest.of(size, page);

        PagedModel<Product> result = categoryService.getAllProductFromCategory(category, pageRequest);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> getProductDetailById(@PathVariable("product_id") String product_id) {
        ProductDto result = productService.getProductDetail(product_id);
        return ResponseEntity.ok(result);
    }
}
