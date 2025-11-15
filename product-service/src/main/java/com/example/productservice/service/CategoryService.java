package com.example.productservice.service;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getAllCategory (){
        return categoryRepository.findAll();
    }

    public List<Product> getAllProductFromCategory(String categoryName){
        Category category = categoryRepository.findByCategoryName(categoryName);
        return productRepository.findProductsByCategory(category);
    }
}
