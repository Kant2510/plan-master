package com.pm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePhaseRequestDTO {
    public String name;
    public Integer boardId;
}
