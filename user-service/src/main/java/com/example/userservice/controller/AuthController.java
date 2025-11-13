package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.JwtService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userService.getUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        else {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String name = body.get("name");

        if (userService.getUserByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(bCryptPasswordEncoder.encode(password));
            newUser.setName(name);

            userService.createUser(newUser);

            String token = jwtService.generateToken(newUser);
            return ResponseEntity.ok(Map.of("token", token));
        }
    }



}
