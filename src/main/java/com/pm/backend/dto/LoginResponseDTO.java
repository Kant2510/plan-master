package com.pm.backend.dto;

public record LoginResponseDTO(
        String message,
        String accessToken,
        String refreshToken
) {}
