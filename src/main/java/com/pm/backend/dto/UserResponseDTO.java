package com.pm.backend.dto;

public record UserResponseDTO(
    String id,
    String idname,
    String fullname,
    String email,
    String avatar
) {}