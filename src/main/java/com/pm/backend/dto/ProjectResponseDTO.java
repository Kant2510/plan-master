package com.pm.backend.dto;

import java.util.List;

public record ProjectResponseDTO(
    Integer id,
    String name,
    List<String> memberIds,
    List<Integer> sprintIds,
    String description,
    String status,
    Integer type,
    Integer sprintCount,
    String creatorId,
    String updaterId,
    String createdAt,
    String updatedAt
) {}
