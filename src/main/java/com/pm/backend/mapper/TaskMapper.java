package com.pm.backend.mapper;

import com.pm.backend.dto.TaskResponseDTO;
import com.pm.backend.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.time.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface TaskMapper {
    @Mapping(target = "id",        expression = "java(task.getId() != null ? task.getId() : null)")
    @Mapping(target = "phaseId",  expression = "java(task.getPhase() != null && task.getPhase().getId() != null ? task.getPhase().getId() : null)")
    @Mapping(target = "assigneeIds",  expression = "java(task.getAssignees() == null ? java.util.List.of() : task.getAssignees().stream().map(t -> t.getMember().getId().toString()).toList())")
    @Mapping(target = "description", expression = "java(toStringOrNull(task.getDescription()))")
    @Mapping(target = "name",        expression = "java(toStringOrNull(task.getName()))")
    @Mapping(target = "isDone",     source = "isDone")
    @Mapping(target = "deadline",   expression = "java(formatTemporal(task.getDeadline()))")
    @Mapping(target = "creatorId", expression = "java(toStringOrNull(task.getUser_created()))")
    @Mapping(target = "updaterId", expression = "java(toStringOrNull(task.getUser_updated()))")
    @Mapping(target = "createdAt", expression = "java(formatTemporal(task.getDate_created()))")
    @Mapping(target = "updatedAt", expression = "java(formatTemporal(task.getDate_updated()))")
    TaskResponseDTO toResponseDto(Task task);

    // ----- Helpers (MapStruct có thể gọi default methods) -----

    default String toStringOrNull(Object o) {
        return o == null ? null : o.toString();
    }

    default String formatTemporal(Object t) {
        return switch (t) {
            case null -> null;
            case Instant i -> i.toString(); // ISO-8601 Z
            case OffsetDateTime odt -> odt.toString();
            case ZonedDateTime zdt -> zdt.toString();
            case LocalDateTime ldt -> ldt.atOffset(ZoneOffset.UTC).toString();
            case java.util.Date d -> d.toInstant().toString();
            default -> t.toString();
        };
    }
}
