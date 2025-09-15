package com.pm.backend.repository;

import com.pm.backend.model.Auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepo extends JpaRepository<Auth, UUID> {
    Optional<Auth> findByEmail (@NotBlank(message = "Email is required") @Email(message = "Email should be a valid email address") String email);
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be a valid email address") String email);
    Optional<Auth> findByUserProfile_Id(UUID userProfileId);
}