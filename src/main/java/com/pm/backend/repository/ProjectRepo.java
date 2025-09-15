package com.pm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pm.backend.model.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {
    @Query("""
         select distinct p
         from Project p
         join p.members pm
         where pm.member.id = :userId
         """)
    List<Project> findByMemberUserId(@Param("userId") UUID userId);
    // Define any additional query methods if needed
}
