package com.example.userservice.controller;

import com.example.userservice.dto.User.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(){
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(Map.of("user",userResponse));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> body){
        String email = body.get("email");
        userService.DeleteAccount(email);
        return ResponseEntity.ok(Map.of("message","success"));
    }

}
