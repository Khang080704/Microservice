package com.example.userservice.controller;

import com.example.userservice.dto.User.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    @PostMapping
    @CacheEvict(allEntries = true, value = "allUsers")
    public ResponseEntity create(@RequestBody User userRequest) {
        try{
            userService.createUser(userRequest);
            return ResponseEntity.ok().body(Map.of("message", "create user successfully"));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
        List<UserResponse> result = userService.getAllUser();
        return ResponseEntity.ok().body(Map.of("list",  result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse result = userService.getUserById(id);
        return ResponseEntity.ok().body(result);
    }
}
