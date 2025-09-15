package com.pm.backend.dto;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
public class RegisterRequestDTO {
    @JsonProperty("id_name")
    @NotBlank(message = "ID name is required")
    private String idName;

    @JsonProperty("fullname")
    @NotBlank(message = "Fullname is required")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Role is required")
    private String role; // Default role set to 'user'

    public @NotBlank(message = "ID name is required") String getIdName() {
        return idName;
    }

    public @NotBlank(message = "Fullname is required") String getFullname() {
        return fullname;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email should be a valid email address") String getEmail() {
        return email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public @NotBlank(message = "Role is required") String getRole() {
        return role;
    }
}
