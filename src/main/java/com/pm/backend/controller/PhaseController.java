package com.pm.backend.controller;

import com.pm.backend.dto.PhaseQueryRequestDTO;
import com.pm.backend.dto.PhaseResponseDTO;
import com.pm.backend.service.PhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phases")
public class PhaseController {
    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<PhaseResponseDTO>> getPhases(@RequestBody PhaseQueryRequestDTO request) {
        // Validate the request
        if (request == null || request.getBoard_id() == null || request.getBoard_id() < 0 ) {
            throw new RuntimeException("Invalid request: board_id cannot be null or blank");
        }
        // Call the service to get scores
        List<PhaseResponseDTO> phases = phaseService.getPhaseByBoardId(request.getBoard_id());
        return phases != null ? ResponseEntity.ok(phases)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
