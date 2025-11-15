package com.example.productservice.repository;

import com.example.productservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    public Category findByCategoryName(String categoryName);
}
