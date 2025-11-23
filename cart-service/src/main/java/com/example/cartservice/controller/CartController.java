package com.example.cartservice.controller;

import com.example.cartservice.dto.AddItemRequest;
import com.example.cartservice.model.Cart;
import com.example.cartservice.model.CartItem;
import com.example.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody AddItemRequest cart) {
        cartService.addProductToCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestBody Map<String,String> body) {
        String userId = body.get("userId");
        List<CartItem> result = cartService.getAllItemsInCart(userId);
        return ResponseEntity.ok().body(result);
    }
}
