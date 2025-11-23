package com.example.cartservice.service;

import com.example.cartservice.dto.AddItemRequest;
import com.example.cartservice.dto.ProductResponse;
import com.example.cartservice.fiegnClient.ProductClient;
import com.example.cartservice.model.Cart;
import com.example.cartservice.model.CartItem;
import com.example.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;

    public void addProductToCart(AddItemRequest request) {
        Cart cart = cartRepository.findCartByUserId(request.getUserId());
        if(cart == null){
            cart = Cart.builder().userId(request.getUserId())
                            .items(new ArrayList<>())
                            .totalPrice(0)
                            .totalQuantity(0)
                            .build();
        }
        ProductResponse product = productClient.getProductById(request.getProductId());

        Optional<CartItem> productExists = cart.getItems().stream()
                .filter(item ->
                        item.getProductId().equals(product.getProduct().getProductId()) &&
                                item.getColorId().equals(request.getColorId()) &&
                                item.getSizeId().equals(request.getSizeId()))
                .findFirst();

        if(productExists.isPresent()){
            CartItem item = productExists.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }
        else {
            CartItem item = new CartItem();
            item.map(product, request.getColorId(), request.getSizeId(), request.getQuantity());
            cart.getItems().add(item);
        }

        cartRepository.save(cart);
    }

    public Cart getAllItemsInCart(String userId){
        Cart cart = cartRepository.findCartByUserId(userId);
        if(cart == null){
            return null;
        }
        else {
            return cart;
        }
    }

    public void removeProductFromCart(String productId, String userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        if(cart == null){
            return;
        }
        else {
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            cartRepository.save(cart);
        }
    }

}
