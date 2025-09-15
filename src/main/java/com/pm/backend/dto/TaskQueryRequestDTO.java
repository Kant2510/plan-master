package com.pm.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class TaskQueryRequestDTO {
    @JsonProperty("phase_id")
    @NotBlank(message = "Phase ID cannot be blank")
    private Integer phase_id;

    public @NotBlank(message = "Phase ID cannot be blank") Integer getPhase_id() {
        return phase_id;
    }
}
