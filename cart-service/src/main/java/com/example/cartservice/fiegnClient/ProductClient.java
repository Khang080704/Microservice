package com.example.cartservice.fiegnClient;

import com.example.cartservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/product/{id}")
    ProductResponse getProductById(@PathVariable("id") String product_id);
}
