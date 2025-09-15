package com.pm.backend.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Configuration
public class AESKeyConfig {
    @Value("${jwt.secret.key}")
    private String rawSecretKey;

    private Key convertStringToKey(String rawKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(rawKey.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert raw key to secret key", e);
        }
    }

    public Key getSecretKey() {
        return convertStringToKey(rawSecretKey);
    }
}
