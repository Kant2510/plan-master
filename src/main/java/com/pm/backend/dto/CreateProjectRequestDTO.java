package com.pm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequestDTO {
    public String name;
    public String description;
    public String createdAt;
}
