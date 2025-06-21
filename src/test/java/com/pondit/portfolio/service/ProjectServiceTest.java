package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.ProjectMapper;
import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import com.pondit.portfolio.persistence.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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