package com.pm.backend.mapper;

import com.pm.backend.dto.ProjectResponseDTO;
import com.pm.backend.model.Project;

import com.pm.backend.model.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.time.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProjectMapper {

    @Mapping(target = "id",        source = "id")
    @Mapping(target = "name",  expression = "java(toStringOrNull(p.getName()))")
    @Mapping(target = "memberIds", expression = "java(mapMemberIds(p))")
    @Mapping(target = "sprintIds", expression = "java(mapSprintIds(p))")
    @Mapping(target = "description", expression = "java(toStringOrNull(p.getDescription()))")
    @Mapping(target = "status",      expression = "java(toStringOrNull(p.getStatus()))")
    @Mapping(target = "type",        source = "type")
    @Mapping(target = "sprintCount", source = "sprintCount")
    @Mapping(target = "creatorId", expression = "java(toStringOrNull(p.getUser_created()))")
    @Mapping(target = "updaterId", expression = "java(toStringOrNull(p.getUser_updated()))")
    @Mapping(target = "createdAt", expression = "java(formatTemporal(p.getDate_created()))")
    @Mapping(target = "updatedAt", expression = "java(formatTemporal(p.getDate_updated()))")
    ProjectResponseDTO toResponseDto(Project p);

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

    default List<String> mapMemberIds(Project p) {
        if (p == null || p.getMembers() == null) return List.of();
        return p.getMembers().stream()
                .map(pm -> pm == null ? null : pm.getMember())      // ProjectMember -> Member(User)
                .filter(m -> m != null && m.getId() != null)
                .map(m -> m.getId().toString())
                .toList();
    }

    default List<Integer> mapSprintIds(Project p) {
        if (p == null || p.getSprints() == null) return List.of();
        return p.getSprints().stream()
                .filter(s -> s != null && s.getId() != null)
                .map(Sprint::getId)
                .toList();
    }
}