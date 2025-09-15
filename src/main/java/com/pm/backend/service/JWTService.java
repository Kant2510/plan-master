package com.pm.backend.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pm.backend.config.AESKeyConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {
    // This service can be used to handle JWT operations such as token generation, validation, etc.
    // For now, it is a placeholder and can be expanded later as needed.
    private final AESKeyConfig aesKeyConfig;
    private final SecretKey secretKey;
    private final long expirationTime;

    public JWTService(AESKeyConfig aesKeyConfig,
                      @Value("${jwt.expiration.time}") long expirationTime) {
        this.aesKeyConfig = aesKeyConfig;
        this.expirationTime = expirationTime;
        this.secretKey = (SecretKey) aesKeyConfig.getSecretKey();
    }

    public String generateToken(UUID id, String email) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        try {
            String email = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("email", String.class);
            if (email == null || email.isEmpty()) {
                throw new JwtException("Invalid JWT token: Email not found");
            }
            return email;
        } catch (SignatureException ex) {
            throw new JwtException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("JWT claims string is empty.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String extractUserID(String token) {
        try {
            String userID = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            if (userID == null || userID.isEmpty()) {
                throw new JwtException("Invalid JWT token: User ID not found");
            }
            return userID;
        } catch (SignatureException ex) {
            throw new JwtException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("JWT claims string is empty.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
