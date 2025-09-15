package com.pm.backend.service;

import com.pm.backend.exception.BoardNotFoundException;
import com.pm.backend.mapper.BoardMapper;
import com.pm.backend.model.Board;
import com.pm.backend.model.Sprint;
import com.pm.backend.repository.SprintRepo;
import org.springframework.stereotype.Service;

import com.pm.backend.dto.BoardResponseDTO;
import com.pm.backend.exception.SprintNotFoundException;
import com.pm.backend.repository.BoardRepo;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepo boardRepo;
    private final BoardMapper boardMapper;
    private final SprintRepo sprintRepo;

    public BoardService(BoardRepo boardRepo, BoardMapper boardMapper, SprintRepo sprintRepo) {
        this.boardRepo = boardRepo;
        this.boardMapper = boardMapper;
        this.sprintRepo = sprintRepo;
    }

    public BoardResponseDTO getBoardByProjectIdAndSprintIndex(Integer projectId, Integer sprintIndex) {
        List<Sprint> sprints = sprintRepo.findByProject_Id(projectId);
        Sprint sprint = sprints.stream().filter(s -> s.getOrderIndex().equals(sprintIndex)).findAny()
                .orElseThrow(() -> new SprintNotFoundException("Sprint not found for project ID: " + projectId + " and sprint index: " + sprintIndex));
        Board board = boardRepo.findBySprint(sprint);
        if (board == null) {
            throw new BoardNotFoundException("No board found for sprint index: " + sprintIndex + " in project ID: " + projectId);
        }
        return boardMapper.toResponseDto(board);
    }
}
