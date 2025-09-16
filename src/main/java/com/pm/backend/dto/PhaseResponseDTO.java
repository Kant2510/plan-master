package com.pm.backend.dto;

import java.util.List;

public record PhaseResponseDTO(
    Integer id,
    String title,
    List<Integer> taskIds
) {}
