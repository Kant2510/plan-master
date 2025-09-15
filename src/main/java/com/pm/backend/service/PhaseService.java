package com.pm.backend.service;

import com.pm.backend.exception.PhaseNotFoundException;
import com.pm.backend.mapper.PhaseMapper;
import com.pm.backend.model.Phase;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.PhaseResponseDTO;
import com.pm.backend.repository.PhaseRepo;

import java.util.List;

@Service
public class PhaseService {
    private final PhaseRepo phaseRepo;
    private final PhaseMapper phaseMapper;

    public PhaseService(PhaseRepo phaseRepo, PhaseMapper phaseMapper) {
        this.phaseRepo = phaseRepo;
        this.phaseMapper = phaseMapper;
    }

    public List<PhaseResponseDTO> getPhaseByBoardId(Integer boardId) {
        List<Phase> phases = phaseRepo.findByBoard_Id(boardId);
        if (phases == null) {
            throw new PhaseNotFoundException("No phase found for board ID: " + boardId);
        }
        return phases.stream().map(phaseMapper::toResponseDto).toList();
    }
}
