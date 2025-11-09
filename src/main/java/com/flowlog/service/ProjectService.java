package com.flowlog.service;

import com.flowlog.dto.ProjectDto;
import com.flowlog.entity.Project;
import com.flowlog.entity.User;
import com.flowlog.enums.ProjectStatus;
import com.flowlog.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllActive()
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjectsByTeam(String teamName) {
        return projectRepository.findByTeamName(teamName)
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ProjectDto createProject(ProjectDto dto, User owner) {
        Project p = Project.builder()
                .name(dto.name())
                .teamName(dto.teamName())
                .description(dto.description())
                .dueDate(dto.dueDate())
                .status(dto.status() != null ? dto.status() : ProjectStatus.IN_PROGRESS)
                .owner(owner)
                .build();
        return ProjectDto.fromEntity(projectRepository.save(p));
    }

    public ProjectDto updateProject(Long id, ProjectDto dto) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        if (dto.name() != null) p.setName(dto.name());
        if (dto.teamName() != null) p.setTeamName(dto.teamName());
        if (dto.description() != null) p.setDescription(dto.description());
        if (dto.dueDate() != null) p.setDueDate(dto.dueDate());
        if (dto.status() != null) p.setStatus(dto.status());
        return ProjectDto.fromEntity(projectRepository.save(p));
    }

    public void deleteProject(Long id) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        p.setDeletedAt(java.time.LocalDateTime.now());
        projectRepository.save(p);
    }
}
