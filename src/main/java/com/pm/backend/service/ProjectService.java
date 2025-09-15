package com.pm.backend.service;

import com.pm.backend.dto.CreateProjectRequestDTO;
import com.pm.backend.model.Project;
import com.pm.backend.model.ProjectMember;
import com.pm.backend.model.UserProfile;
import com.pm.backend.repository.ProjectMemberRepo;
import com.pm.backend.repository.ProjectRepo;
import com.pm.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.ProjectResponseDTO;
import com.pm.backend.mapper.ProjectMapper;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService {
    // This class will handle the logic for generating reports
    // It will interact with the database to fetch data and format it into reports
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;
    private final ProjectMemberRepo projectMemberRepo;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepo projectRepo, UserRepo userRepo, ProjectMemberRepo projectMemberRepo, ProjectMapper projectMapper) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.projectMemberRepo = projectMemberRepo;
        this.projectMapper = projectMapper;
    }


    // Example method to generate a report
    public List<ProjectResponseDTO> getAllProjects(String userId) {
        // Logic to generate a report
        UUID userUUID = UUID.fromString(userId);
        // Find the projects for the given user
        List<Project> projects = projectRepo.findByMemberUserId(userUUID);

        return projects.stream().map(projectMapper::toResponseDto).toList();
    }

    public ProjectResponseDTO createProject(String userId, CreateProjectRequestDTO request) {
        UUID userUUID = UUID.fromString(userId);
        Project project = Project.builder()
                .name(request.name)
                .description(request.description)
                .date_created(Instant.parse(request.createdAt))
                .user_created(userUUID)
                .sprintCount(0)
                .build();

        // Save the new project
        Project savedProject = projectRepo.save(project);

        // Create the project member entry
        UserProfile user = userRepo.findById(userUUID).orElseThrow();
        ProjectMember pm = ProjectMember.builder()
                .project(savedProject)
                .member(user)
                .role("none")
                .build();

        // Use a modifiable Set instead of an immutable one
        Set<ProjectMember> members = new HashSet<>();
        members.add(pm);

        // Update the saved project's members
        savedProject.setMembers(members);

        // Save the project and its members
        projectRepo.save(savedProject);
        projectMemberRepo.save(pm);

        return projectMapper.toResponseDto(savedProject);
    }

    public ProjectResponseDTO createSprint(String userId, String projectId) {
        UUID userUUID = UUID.fromString(userId);
        Integer projId = Integer.parseInt(projectId);
        Project project = projectRepo.findById(projId).orElseThrow();

        // Increment the sprint count
        int newSprintCount = project.getSprintCount() + 1;
        project.setSprintCount(newSprintCount);
        project.setUser_updated(userUUID);
        project.setDate_updated(Instant.now());

        // Save the updated project
        Project updatedProject = projectRepo.save(project);

        return projectMapper.toResponseDto(updatedProject);
    }
}
