package com.example.productservice.controller;

import com.example.productservice.model.Category;
import com.example.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<Category> result = categoryService.getAllCategory();
        return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> addNewCategory(@RequestBody Map<String, String> body) {
        String categoryName = body.get("categoryName");
        Category saved = categoryService.addNewCategory(categoryName);
        return ResponseEntity.ok().body(saved);
    }
}
