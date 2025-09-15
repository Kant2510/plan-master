package com.pm.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserRequestByIdDTO {
    @JsonProperty("user_id")
    @NotBlank(message = "User ID cannot be blank")
    private String user_id;

    public @NotBlank(message = "User ID cannot be blank") String getUser_id() {
        return user_id;
    }
}
