package com.example.productservice.repository;

import com.example.productservice.model.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends MongoRepository<Size,String> {
}
