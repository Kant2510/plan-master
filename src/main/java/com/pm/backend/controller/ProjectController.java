package com.pm.backend.controller;

import com.pm.backend.dto.CreateProjectRequestDTO;
import com.pm.backend.dto.ProjectResponseDTO;
import com.pm.backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService reportService) {
        this.projectService = reportService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProjectResponseDTO> projects = projectService.getAllProjects(userName);
        return projects != null ? ResponseEntity.ok(projects)
                              : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody CreateProjectRequestDTO request) {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProjectResponseDTO project = projectService.createProject(userName, request);
        return project != null ? ResponseEntity.ok(project)
                              : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/sprint/create")
    public ResponseEntity<ProjectResponseDTO> createSprint(@RequestParam String projectId) {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProjectResponseDTO project = projectService.createSprint(userName, projectId);
        return project != null ? ResponseEntity.ok(project)
                              : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
