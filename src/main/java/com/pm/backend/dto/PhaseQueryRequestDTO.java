package com.pm.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class PhaseQueryRequestDTO {
    @JsonProperty("board_id")
    @NotBlank(message = "Board ID cannot be blank")
    private Integer board_id;

    public @NotBlank(message = "Board ID cannot be blank") Integer getBoard_id() {
        return board_id;
    }
}
