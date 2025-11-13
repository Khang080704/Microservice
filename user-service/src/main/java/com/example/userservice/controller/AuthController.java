package com.example.userservice.controller;

import com.example.userservice.dto.User.RegisterRequest;
import com.example.userservice.entity.AuthUser;
import com.example.userservice.entity.User;
import com.example.userservice.service.AuthService;
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
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        AuthUser authUser = authService.getUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if(!bCryptPasswordEncoder.matches(password, authUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        else {
            String accessToken = jwtService.generateAccessToken(authUser.getUser().getFullName(), authUser.getUser().getId());
            String refreshToken = jwtService.generateRefreshToken(authUser.getUser().getFullName(), authUser.getUser().getId());
            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();

        if (authService.getUserByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        else {
            authService.createUser(registerRequest);
            return ResponseEntity.ok(Map.of("message", "Register successful"));
        }
    }

}
