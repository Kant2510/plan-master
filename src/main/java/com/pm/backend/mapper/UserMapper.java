package com.pm.backend.mapper;

import com.pm.backend.dto.UserResponseDTO;
import com.pm.backend.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponseDto(UserProfile user);
}