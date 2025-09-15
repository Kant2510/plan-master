package com.pm.backend.service;

import com.pm.backend.exception.UserNotFoundException;
import com.pm.backend.model.Project;
import com.pm.backend.repository.ProjectRepo;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.UserResponseDTO;
import com.pm.backend.mapper.UserMapper;
import com.pm.backend.model.UserProfile;
import com.pm.backend.repository.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, ProjectRepo projectRepo, UserMapper userMapper ) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
        this.userMapper = userMapper;
    }
    /**
     * Get all users.
     *
     * @return List of UserResponseDTO containing user profile information
     */
    public List<UserResponseDTO> getAllUsers() {
        List<UserProfile> userProfiles = userRepo.findAll();
        return userProfiles.stream().map(userMapper::toResponseDto).toList();
    }
    /**
     * Get user target by userId.
     *
     * @param userIdString the user ID as a string
     * @return UserTargetResponseDTO containing the user's target information
     */
    public UserResponseDTO getProfile(String userId) {
        UUID userUUID = UUID.fromString(userId);
        UserProfile user = userRepo.findById(userUUID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.logger();
        return userMapper.toResponseDto(user);
    }

    public List<UserResponseDTO> getUsersByProjectId(Integer projectId) {
        Optional<Project> project = projectRepo.findById(projectId);
        if (project.isEmpty()) {
            throw new UserNotFoundException("Project not found");
        }
        return project.get().getMembers().stream().map(pm -> userMapper.toResponseDto(pm.getMember())).toList();
    }
}