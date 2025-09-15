package com.pm.backend.mapper;

import com.pm.backend.dto.BoardResponseDTO;
import com.pm.backend.model.Board;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BoardMapper {
    @Mapping(target = "id",        expression = "java(board.getId() != null ? board.getId() : null)")
    @Mapping(target = "sprintId",  expression = "java(board.getSprint() != null && board.getSprint().getId() != null ? board.getSprint().getId() : null)")
    @Mapping(target = "phaseIds",  expression = "java(board.getPhases() == null ? java.util.List.of() : board.getPhases().stream().map(p -> p.getId()).toList())")
    BoardResponseDTO toResponseDto(Board board);
}