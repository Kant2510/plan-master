package com.pm.backend.controller;

import com.pm.backend.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pm.backend.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/status")
    public ResponseEntity<MessageResponseDTO> checkAuth() {
        MessageResponseDTO message = new MessageResponseDTO("Authenticated");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        if (request.getEmail() == null || request.getPassword() == null ||
                request.getFullname() == null || request.getIdName() == null ||
                request.getRole() == null) {
            return ResponseEntity.badRequest().build();
        }
        RegisterResponseDTO registerResult = authService.register(request);
        MessageResponseDTO message = new MessageResponseDTO("Go back to home page to login.");
        return registerResult != null
                ? ResponseEntity.ok(message)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO authResult = authService.authenticate(request);

        return authResult != null
                ? ResponseEntity.ok(authResult)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}