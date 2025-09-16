package com.pm.backend.service;

import com.pm.backend.constant.SprintConstant;
import com.pm.backend.dto.CreateProjectRequestDTO;
import com.pm.backend.model.*;
import com.pm.backend.repository.*;
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
    private final SprintRepo sprintRepo;
    private final BoardRepo boardRepo;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepo projectRepo, UserRepo userRepo, ProjectMemberRepo projectMemberRepo, SprintRepo sprintRepo, BoardRepo boardRepo, ProjectMapper projectMapper) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.projectMemberRepo = projectMemberRepo;
        this.sprintRepo = sprintRepo;
        this.boardRepo = boardRepo;
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
                .joinedAt(Instant.parse(request.createdAt))
                .build();
        projectMemberRepo.save(pm);

        return projectMapper.toResponseDto(savedProject);
    }

    public ProjectResponseDTO createSprint(String userId, Integer projectId) {
        UUID userUUID = UUID.fromString(userId);
        Project project = projectRepo.findById(projectId).orElseThrow();

        // Increment the sprint count
        int newSprintCount = project.getSprintCount() + 1;
        project.setSprintCount(newSprintCount);
        project.setUser_updated(userUUID);
        project.setDate_updated(Instant.now());

        Sprint newSprint = Sprint.builder()
                .orderIndex(newSprintCount)
                .start_date(Instant.now())
                .end_date(Instant.now().plusSeconds(7 * 24 * 60 * 60)) // Default to one week later
                .status(SprintConstant.SPRINT_STATUS.PLANNED)
                .project(project)
                .user_created(userUUID)
                .build();
        Sprint savedSprint = sprintRepo.save(newSprint);

        Board newBoard = Board.builder()
                .sprint(savedSprint)
                .build();
        boardRepo.save(newBoard);

        return projectMapper.toResponseDto(project);
    }
}
