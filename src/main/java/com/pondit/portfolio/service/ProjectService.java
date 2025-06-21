package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.ProjectMapper;
import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import com.pondit.portfolio.persistence.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        return entityList.stream().map(entity -> {
            return projectMapper.entityToDomain(entity);
        }).toList();
    }

    public Project createProject(CreateProjectRequest request) {
        // request
        String name = request.name();
        String description = request.description();

        // save to database
        ProjectEntity entity = new ProjectEntity();
        entity.setName(name);
        entity.setDescription(description);
        ProjectEntity savedEntity = projectRepository.save(entity); // create operation

        // map entity to domain object
        Long savedEntityId = savedEntity.getId();
        String savedEntityName = savedEntity.getName();
        String savedEntityDescription = savedEntity.getDescription();
        return new Project(savedEntityId, savedEntityName, savedEntityDescription);
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
        // request
        String description = request.description();

        // query existing project
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(id);
        if (projectEntityOptional.isEmpty()) {
            throw new NotFoundException("Project not found");
        }

        // save to database
        ProjectEntity projectEntity = projectEntityOptional.get();
        projectEntity.setDescription(description);
        projectRepository.save(projectEntity);
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
