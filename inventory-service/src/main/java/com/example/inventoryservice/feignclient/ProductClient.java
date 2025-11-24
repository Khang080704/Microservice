package com.example.inventoryservice.feignclient;

import com.example.inventoryservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/product")
    List<ProductResponse> getProducts();
}
