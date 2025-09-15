package com.pm.backend.dto;

import java.util.List;

public record BoardResponseDTO(
    Integer id,
    Integer sprintId,
    List<Integer> phaseIds
) {}
