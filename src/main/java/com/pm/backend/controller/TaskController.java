package com.pm.backend.controller;

import com.pm.backend.dto.CreateTaskRequestDTO;
import com.pm.backend.dto.MessageResponseDTO;
import com.pm.backend.dto.TaskQueryRequestDTO;
import com.pm.backend.dto.TaskResponseDTO;
import com.pm.backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@RequestBody TaskQueryRequestDTO request) {
        // Validate the request
        if (request == null || request.getPhase_id() == null || request.getPhase_id() < 0 ) {
            throw new RuntimeException("Invalid request: phase_id cannot be null or blank");
        }
        // Call the service to get scores
        List<TaskResponseDTO> tasks = taskService.getTaskByPhaseId(request.getPhase_id());
        return tasks != null ? ResponseEntity.ok(tasks)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/complete")
    public ResponseEntity<MessageResponseDTO> completeTask(@RequestParam Integer taskId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Call the service to get scores
        TaskResponseDTO task = taskService.completeTask(userId, taskId);
        return task != null ? ResponseEntity.ok(new MessageResponseDTO("Task marked as complete successfully."))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO request) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Call the service to get scores
        TaskResponseDTO task = taskService.createTask(userId, request);
        return task != null ? ResponseEntity.ok(task)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
