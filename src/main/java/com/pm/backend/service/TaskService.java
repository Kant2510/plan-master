package com.pm.backend.service;

import com.pm.backend.dto.CreateTaskRequestDTO;
import com.pm.backend.exception.TaskNotFoundException;
import com.pm.backend.mapper.TaskMapper;
import com.pm.backend.model.Phase;
import com.pm.backend.model.Task;
import com.pm.backend.repository.PhaseRepo;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.TaskResponseDTO;
import com.pm.backend.repository.TaskRepo;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final PhaseRepo phaseRepo;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepo taskRepo, PhaseRepo phaseRepo, TaskMapper taskMapper) {
        this.taskRepo = taskRepo;
        this.phaseRepo = phaseRepo;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponseDTO> getTaskByPhaseId(Integer phaseId) {
        List<Task> tasks = taskRepo.findByPhase_Id(phaseId);
        if (tasks == null) {
            throw new TaskNotFoundException("No task found for phase ID: " + phaseId);
        }
        return tasks.stream().map(taskMapper::toResponseDto).toList();
    }

    public TaskResponseDTO completeTask(Integer taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));
        Boolean previousStatus = task.getIsDone();
        task.setIsDone(!previousStatus); // Toggle the isDone status
        Task updatedTask = taskRepo.save(task);
        return taskMapper.toResponseDto(updatedTask);
    }

    public TaskResponseDTO createTask(CreateTaskRequestDTO request) {
        // Validate input fields to ensure they are not null or empty
        if (request.name == null || request.name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be null or blank");
        }
        if (request.description == null || request.description.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be null or blank");
        }
        if (request.deadline == null || request.createdAt == null) {
            throw new IllegalArgumentException("Deadline and creation date cannot be null");
        }

        Phase phase = phaseRepo.findById(request.phaseId)
                .orElseThrow(() -> new TaskNotFoundException("Phase not found with ID: " + request.phaseId));

        Instant deadline;
        Instant createdAt;

        try {
            // Parse the input dates safely
            deadline = Instant.parse(request.deadline);
            createdAt = Instant.parse(request.createdAt);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: Ensure dates follow ISO-8601 (e.g., 2025-09-15T10:15:30Z)", e);
        }

        Task task = Task.builder()
                .name(request.name)
                .description(request.description)
                .isDone(false) // Tasks are incomplete by default
                .phase(phase) // Validated phase object
                .deadline(deadline)
                .date_created(createdAt)
                .build();

        // Save the task and transform the entity to DTO
        Task savedTask = taskRepo.save(task);
        return taskMapper.toResponseDto(savedTask);
    }
}
