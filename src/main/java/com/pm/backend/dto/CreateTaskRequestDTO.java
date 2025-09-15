package com.pm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequestDTO {
    public String name;
    public String description;
    public Integer phaseId;
    public String deadline;
    public String createdAt;
}
