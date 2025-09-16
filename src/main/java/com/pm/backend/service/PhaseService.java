package com.pm.backend.service;

import com.pm.backend.dto.CreatePhaseRequestDTO;
import com.pm.backend.exception.PhaseNotFoundException;
import com.pm.backend.mapper.PhaseMapper;
import com.pm.backend.model.Board;
import com.pm.backend.model.Phase;
import com.pm.backend.repository.BoardRepo;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.PhaseResponseDTO;
import com.pm.backend.repository.PhaseRepo;

import java.util.List;

@Service
public class PhaseService {
    private final PhaseRepo phaseRepo;
    private final BoardRepo boardRepo;
    private final PhaseMapper phaseMapper;

    public PhaseService(PhaseRepo phaseRepo, BoardRepo boardRepo, PhaseMapper phaseMapper) {
        this.phaseRepo = phaseRepo;
        this.boardRepo = boardRepo;
        this.phaseMapper = phaseMapper;
    }

    public List<PhaseResponseDTO> getPhaseByBoardId(Integer boardId) {
        List<Phase> phases = phaseRepo.findByBoard_Id(boardId);
        if (phases == null) {
            throw new PhaseNotFoundException("No phase found for board ID: " + boardId);
        }
        return phases.stream().map(phaseMapper::toResponseDto).toList();
    }

    public PhaseResponseDTO createPhase(CreatePhaseRequestDTO request) {
        // Validate input fields to ensure they are not null or empty
        if (request.name == null || request.name.isBlank()) {
            throw new IllegalArgumentException("Phase name cannot be null or blank");
        }
        Board board = boardRepo.findById(request.boardId)
                .orElseThrow(() -> new PhaseNotFoundException("Board not found with ID: " + request.boardId));
        int maxOrderIndex = board.getPhases().toArray().length - 1;
        Phase phase = Phase.builder()
                .title(request.name)
                .board(board)
                .orderIndex(maxOrderIndex + 1)
                .tasks(List.of())
                .build();
        Phase savedPhase = phaseRepo.save(phase);

        return phaseMapper.toResponseDto(savedPhase);
    }
}
