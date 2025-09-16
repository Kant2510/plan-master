package com.pm.backend.mapper;

import com.pm.backend.dto.PhaseResponseDTO;
import com.pm.backend.model.Phase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PhaseMapper {
    @Mapping(target = "id",        expression = "java(phase.getId() != null ? phase.getId() : null)")
    @Mapping(target = "title",     expression = "java(phase.getTitle() != null ? phase.getTitle() : null)")
    @Mapping(target = "taskIds",  expression = "java(phase.getTasks() == null ? java.util.List.of() : phase.getTasks().stream().map(t -> t.getId()).toList())")
    PhaseResponseDTO toResponseDto(Phase phase);
}
