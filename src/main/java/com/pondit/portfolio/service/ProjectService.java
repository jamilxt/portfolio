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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<Project> getAllProjects(Pageable pageable) {
        List<ProjectEntity> entityList = projectRepository.findAll(pageable).getContent();
        return entityList.stream().map(projectMapper::entityToDomain).toList();
    }

    public Long createProject(CreateProjectRequest request) {
        var entityToSave = projectMapper.createRequestToEntity(request);
        var savedEntity = projectRepository.save(entityToSave);
        return savedEntity.getId();
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project domain object
     * @throws NotFoundException if the project with the specified ID is not found
     */
    public Project getProjectById(Long id) throws NotFoundException {
        var projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
        return projectMapper.entityToDomain(projectEntity);
    }

    public void updateProject(Long id, UpdateProjectRequest request) throws NotFoundException {
        var projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
        var updatedProjectEntity = projectMapper.updateRequestToEntity(request, projectEntity);
        projectRepository.save(updatedProjectEntity);
    }

    public void deleteProject(Long id) throws NotFoundException {
        // query existing project
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(id);
        if (projectEntityOptional.isEmpty()) {
            throw new NotFoundException("Project not found");
        }

        // delete from database
        projectRepository.deleteById(id);
    }
}
