package com.example.productservice.repository;

import com.example.productservice.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand,String> {
}
