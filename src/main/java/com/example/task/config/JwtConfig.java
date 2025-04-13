package com.example.task.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtConfig {
    private final String jwtSecret = "XyP9eTfR5Xy67uD2kMz5qWlV1+vF9Ekt2V8pLwKs2Hg=";
    private final long expirationMs = 86400000; // 1 hari

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractUserId(token); // akan throw kalau invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
