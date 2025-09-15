package com.pm.backend.dto;

import java.util.List;

public record TaskResponseDTO(
    Integer id,
    String name,
    String description,
    Integer phaseId,
    List<String> assigneeIds,
    String deadline,
    Boolean isDone,
    String createdAt,
    String creatorId,
    String updatedAt,
    String updaterId
) {}
