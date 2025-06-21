package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.ProjectMapper;
import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import com.pondit.portfolio.persistence.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<Project> getAll(Pageable pageable) {
        List<ProjectEntity> entityList = projectRepository.findAll(pageable).getContent();
        return entityList.stream().map(projectMapper::entityToDomain).toList();
    }

    public Long create(CreateProjectRequest request) {
        var entityToSave = projectMapper.createRequestToEntity(request);
        var savedEntity = projectRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public Project getById(Long id) throws NotFoundException {
        var projectEntity = this.findEntityById(id);
        return projectMapper.entityToDomain(projectEntity);
    }

    public void update(Long id, UpdateProjectRequest request) throws NotFoundException {
        var projectEntity = this.findEntityById(id);
        var updatedProjectEntity = projectMapper.updateRequestToEntity(request, projectEntity);
        projectRepository.save(updatedProjectEntity);
    }

    public void delete(Long id) throws NotFoundException {
        this.findEntityById(id);
        projectRepository.deleteById(id);
    }

    private ProjectEntity findEntityById(Long id) throws NotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }
}
