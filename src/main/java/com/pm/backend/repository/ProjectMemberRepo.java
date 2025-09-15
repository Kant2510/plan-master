package com.pm.backend.repository;

import com.pm.backend.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepo extends JpaRepository<ProjectMember, Integer> {
}
