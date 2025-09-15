package com.pm.backend.service;

import com.pm.backend.dto.RegisterResponseDTO;
import com.pm.backend.model.UserProfile;
import com.pm.backend.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pm.backend.constant.AuthConstant;
import com.pm.backend.dto.RegisterRequestDTO;
import com.pm.backend.dto.LoginRequestDTO;
import com.pm.backend.dto.LoginResponseDTO;
import com.pm.backend.model.Auth;
import com.pm.backend.repository.AuthRepo;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthRepo authRepo;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthService(AuthRepo authRepo,
                       UserRepo userRepo,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JWTService jwtService) {
        this.authRepo = authRepo;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (authRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UUID roleId = Objects.equals(request.getRole(), AuthConstant.END_USER_ROLE)
                ? AuthConstant.END_USER_ROLE_UUID : null;
        if (roleId == null) {
            throw new RuntimeException("Invalid role specified");
        }

        Auth newAuth = new Auth.Builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .provider(AuthConstant.PROVIDER_TYPE.local.name())
                .isBanned(false)
                .role(roleId)
                .build();

        UserProfile newUserProfile = UserProfile.builder()
                .idname(request.getIdName())
                .email(request.getEmail())
                .fullname(request.getFullname())
                .avatar("") // Default avatar URL
                .build();

        UserProfile createdUser = userRepo.save(newUserProfile);
        createdUser.logger();

        newAuth.BuildMore().userProfile(newUserProfile);

        Auth savedAuth = authRepo.save(newAuth);
        savedAuth.logger();

        return getRegisterResponseDTO(savedAuth);
    }

    private static RegisterResponseDTO getRegisterResponseDTO(Auth savedAuth) {
        return new RegisterResponseDTO(
                savedAuth.getUserProfile().getId().toString(),
                savedAuth.getUserProfile().getIdname(),
                savedAuth.getEmail(),
                savedAuth.getUserProfile().getFullname(),
                savedAuth.getUserProfile().getAvatar(),
                savedAuth.getProvider(),
                savedAuth.getRole().toString(),
                savedAuth.getIsBanned()
        );
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        Auth auth = authRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        try{
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(auth.getUserProfile().getId().toString(), request.getPassword());
            authenticationManager.authenticate(authInputToken);

            String token = jwtService.generateToken(auth.getUserProfile().getId(), auth.getEmail());

            return new LoginResponseDTO("Log in successfully!", token, "");
        } catch(AuthenticationException authExc){
            throw new RuntimeException("Invalid username/password.");
        }
    }
}