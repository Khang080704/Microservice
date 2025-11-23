package com.example.cartservice.repository;

import com.example.cartservice.model.Cart;
import com.example.cartservice.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findCartByUserId(String userId);
}
