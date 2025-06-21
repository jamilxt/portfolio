package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.ProjectMapper;
import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import com.pondit.portfolio.persistence.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Test
    void getAllProjects_returns_project_list() {
        // given
        Pageable pageable = Pageable.unpaged();
        ProjectEntity entity1 = new ProjectEntity();
        entity1.setId(1L);
        entity1.setName("Project 1");
        entity1.setDescription("Description 1");

        ProjectEntity entity2 = new ProjectEntity();
        entity2.setId(2L);
        entity2.setName("Project 2");
        entity2.setDescription("Description 2");

        List<ProjectEntity> entities = List.of(entity1, entity2);

        // Mock the repository to return a Page with the entities
        when(projectRepository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(projectMapper.entityToDomain(entity1)).thenReturn(new Project(1L, "Project 1", "Description 1"));
        when(projectMapper.entityToDomain(entity2)).thenReturn(new Project(2L, "Project 2", "Description 2"));

        // when
        List<Project> result = projectService.getAllProjects(pageable);

        // then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals("Project 1", result.get(0).getName());
        Assertions.assertEquals("Description 1", result.get(0).getDescription());
        Assertions.assertEquals(2L, result.get(1).getId());
        Assertions.assertEquals("Project 2", result.get(1).getName());
        Assertions.assertEquals("Description 2", result.get(1).getDescription());
    }

    @Test
    void createProject_saves_and_returns_project() {
        // given
        CreateProjectRequest request = new CreateProjectRequest("New Project", "New Description");
        ProjectEntity entityToSave = new ProjectEntity();
        entityToSave.setName("New Project");
        entityToSave.setDescription("New Description");

        ProjectEntity savedEntity = new ProjectEntity();
        savedEntity.setId(1L);
        savedEntity.setName("New Project");
        savedEntity.setDescription("New Description");

        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(savedEntity);

        // when
        Project result = projectService.createProject(request);

        // then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("New Project", result.getName());
        Assertions.assertEquals("New Description", result.getDescription());
    }

    @Test
    void given_valid_id_return_a_project() throws NotFoundException {
        // given/setup
        long expectedId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(expectedId);
        projectEntity.setName("Test Project");
        projectEntity.setDescription("Test Description");

        when(projectRepository.findById(expectedId)).thenReturn(Optional.of(projectEntity));
        when(projectMapper.entityToDomain(projectEntity)).
                thenReturn(new Project(expectedId, "Test Project", "Test Description"));

        // when
        Project project = projectService.getProjectById(expectedId);

        // then/verify
        Assertions.assertEquals(expectedId, project.getId());
        Assertions.assertEquals("Test Project", project.getName());
        Assertions.assertEquals("Test Description", project.getDescription());
    }

    @Test
    void given_invalid_id_throw_not_found_exception() {
        // given/setup
        long invalidId = 999L;
        when(projectRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> projectService.getProjectById(invalidId));
    }
}