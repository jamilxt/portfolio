package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project entityToDomain(ProjectEntity entity) {
        Project domain = new Project();
        BeanUtils.copyProperties(entity, domain);
        return domain;
    }

    public ProjectEntity createRequestToEntity(CreateProjectRequest request) {
        ProjectEntity entity = new ProjectEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public ProjectEntity updateRequestToEntity(UpdateProjectRequest request, ProjectEntity entity) {
        entity.setDescription(request.description());
        return entity;
    }
}
