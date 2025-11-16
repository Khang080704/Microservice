package com.example.userservice.service;

import com.example.userservice.dto.User.RegisterRequest;
import com.example.userservice.entity.AuthUser;
import com.example.userservice.entity.User;
import com.example.userservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<AuthUser> getUserByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    public void createUser(RegisterRequest request) {
        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.getEmail());
        authUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authUser.setRole(request.getRole());

        User user = new User();
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());

        authUser.setUser(user);
        authUserRepository.save(authUser);
    }

    public void deleteAccount(String email) {
        authUserRepository.removeAuthUserByEmail(email);
    }
}
