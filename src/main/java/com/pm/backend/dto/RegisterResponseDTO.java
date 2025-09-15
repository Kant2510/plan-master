package com.pm.backend.dto;

public record RegisterResponseDTO(
    String id,
    String idName,
    String email,
    String fullname,
    String avatar,
    String provider,
    String role,
    Boolean isBanned
) {}
