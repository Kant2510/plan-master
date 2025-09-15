package com.pm.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserRequestByProjectDTO {
    @JsonProperty("project_id")
    @NotBlank(message = "Phase ID cannot be blank")
    private Integer project_id;

    public @NotBlank(message = "Phase ID cannot be blank") Integer getProject_id() {
        return project_id;
    }
}
