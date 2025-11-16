package com.example.userservice.service;

import com.example.userservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    private static final String SECRET_KEY = "my-very-long-and-secure-jwt-secret-key-123456";

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;  // 15 phút
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 ngày

    public String generateAccessToken(String userName, UUID user_id, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user_id);
        claims.put("role", role);
        return createToken(claims, userName, ACCESS_TOKEN_EXPIRATION);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateRefreshToken(String userName, UUID user_id, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user_id);
        claims.put("role", role);
        return createToken(claims, userName, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        return extractClaims(token).getExpiration().after(new Date());
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("user_id", Long.class);
    }

}
