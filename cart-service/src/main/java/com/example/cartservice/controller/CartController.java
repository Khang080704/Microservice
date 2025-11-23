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
        Cart result = cartService.getAllItemsInCart(userId);
        return ResponseEntity.ok().body(Map.of("user_id", userId, "items", result.getItems(),
                "totalPrice", result.calculateTotalPrice(),
                "totalQuantity", result.calculateTotalQuantity()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProductInCart(@RequestBody Map<String,String> body) {
        String userId = body.get("userId");
        String productId = body.get("productId");
        cartService.removeProductFromCart(productId, userId);
        return ResponseEntity.ok().body(Map.of("message", "remove successfully"));
    }
}
