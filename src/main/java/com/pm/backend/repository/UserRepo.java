package com.pm.backend.repository;

import com.pm.backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByEmail(String email);
}
