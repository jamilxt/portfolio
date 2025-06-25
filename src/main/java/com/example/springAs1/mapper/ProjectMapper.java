package com.example.springAs1.mapper;

import com.example.springAs1.model.domain.Project;
import com.example.springAs1.model.dto.CreateProjectRequest;
import com.example.springAs1.model.dto.UpdateProjectRequest;
import com.example.springAs1.persistence.entity.ProjectEntity;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder

public class ProjectMapper {
    public Project toDomain(ProjectEntity entity){
        return Project.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .name(entity.getName())
                .build();
    }
    public ProjectEntity createRequestToEntity(CreateProjectRequest dto){
        return ProjectEntity.builder()
                .name(
                        (dto.name() == null || dto.name().isBlank())
                                ? "Untitled" : dto.name())
                .description(
                        (dto.description() == null || dto.description().isBlank())
                                ? "No description": dto.description())
                .build();
    }
    public ProjectEntity updateRequestToEntity(UpdateProjectRequest dto, ProjectEntity projectEntity){
        projectEntity.setName((
                        (dto.name() == null || dto.name().isBlank())
                                ? projectEntity.getName() : dto.name()));
        projectEntity.setDescription(
                        (dto.description() == null || dto.description().isBlank())
                                ? projectEntity.getDescription() : dto.description());
        projectEntity.setId(projectEntity.getId());
        return projectEntity;
    }
}
