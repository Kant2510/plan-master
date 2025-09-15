package com.pm.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryRequestDTO {
    @JsonProperty("project_id")
    @NotBlank(message = "Project ID cannot be blank")
    private Integer project_id;

    @JsonProperty("sprint_index")
    @NotBlank(message = "Sprint index cannot be blank")
    private Integer sprint_index;

    public @NotBlank(message = "Project ID cannot be blank") Integer getProject_id() {
        return project_id;
    }

    public @NotBlank(message = "Sprint index cannot be blank") Integer getSprint_index() {
        return sprint_index;
    }
}
