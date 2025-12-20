package com.example.productservice.service;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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

    public PagedModel<Product> getAllProductFromCategory(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return new PagedModel<>(products);
    }

    public Category addNewCategory(String categoryName) {
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryName);
        return categoryRepository.save(newCategory);
    }
}
