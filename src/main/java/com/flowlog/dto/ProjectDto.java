package com.flowlog.dto;

import com.flowlog.entity.Project;
import com.flowlog.enums.ProjectStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProjectDto(
        Long id,
        String name,
        String teamName,
        String description,
        LocalDate dueDate,
        ProjectStatus status,
        String ownerName
) {
    public static ProjectDto fromEntity(Project p) {
        return ProjectDto.builder()
                .id(p.getId())
                .name(p.getName())
                .teamName(p.getTeamName())
                .description(p.getDescription())
                .dueDate(p.getDueDate())
                .status(p.getStatus())
                .ownerName(p.getOwner() != null ? p.getOwner().getName() : null)
                .build();
    }
}
