package com.example.userservice.service;

import com.example.userservice.dto.User.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService  authService;

    public UserResponse getCurrentUser() {
        UUID user_id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =  userRepository.findById(user_id).orElse(null);
        return new UserResponse(user.getFullName(), user.getPhoneNumber(), user.getAddress());
    }

    @Transactional
    public void DeleteAccount(String email) {
        authService.deleteAccount(email);
    }
}
