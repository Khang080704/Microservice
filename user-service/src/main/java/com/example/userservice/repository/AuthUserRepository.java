package com.example.userservice.repository;

import com.example.userservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    public Optional<AuthUser> findByEmail(String email);
    public void removeAuthUserByEmail(String email);
}
