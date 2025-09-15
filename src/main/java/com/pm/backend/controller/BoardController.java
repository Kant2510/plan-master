package com.pm.backend.controller;

import com.pm.backend.dto.BoardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pm.backend.dto.BoardQueryRequestDTO;
import com.pm.backend.service.BoardService;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/search")
    public ResponseEntity<BoardResponseDTO> getScores(@RequestBody BoardQueryRequestDTO request) {
        // Validate the request
        if (request == null || request.getProject_id() == null || request.getProject_id() < 0 || request.getSprint_index() == null || request.getSprint_index() < 0) {
            throw new RuntimeException("Invalid request: project_id and sprint_index cannot be null or blank");
        }
        // Call the service to get scores
        BoardResponseDTO board = boardService.getBoardByProjectIdAndSprintIndex(request.getProject_id(), request.getSprint_index());
        return board != null ? ResponseEntity.ok(board)
                             : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
