package com.pm.backend.controller;

import com.pm.backend.dto.UserRequestByIdDTO;
import com.pm.backend.dto.UserRequestByProjectDTO;
import org.springframework.web.bind.annotation.*;

import com.pm.backend.dto.UserResponseDTO;
import com.pm.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Define endpoints for user operations here,
    // For example, to get all users:
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/profile")
    public UserResponseDTO getProfile(@RequestBody UserRequestByIdDTO request) {
        return userService.getProfile(request.getUser_id());
    }

    @PostMapping("/project")
    public List<UserResponseDTO> getUserByProject(@RequestBody UserRequestByProjectDTO request) {
        return userService.getUsersByProjectId(request.getProject_id());
    }
}