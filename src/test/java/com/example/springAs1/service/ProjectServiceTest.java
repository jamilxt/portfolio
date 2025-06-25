package com.example.springAs1.service;

import com.example.springAs1.exception.custom.NotFoundException;
import com.example.springAs1.mapper.ProjectMapper;
import com.example.springAs1.model.domain.Project;
import com.example.springAs1.model.dto.CreateProjectRequest;
import com.example.springAs1.model.dto.UpdateProjectRequest;
import com.example.springAs1.persistence.entity.ProjectEntity;
import com.example.springAs1.persistence.repository.ProjectRepository;
//import com.example.springAs1.service.ProjectService;
import com.example.springAs1.service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
//@RequiredArgsConstructor
public class ProjectServiceTest {
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Test
    void createProject_saves_and_returns_(){
        // given
        CreateProjectRequest request = new CreateProjectRequest("New project", "Project description");
        ProjectEntity entityToSave = ProjectEntity.builder()
                .description("Project description")
                .name("New project")
                .build();
        System.out.println("================== entity to saved "+entityToSave);
        ProjectEntity savedEntity = ProjectEntity.builder()
                .description("Project description")
                .name("New project")
                .id(1L)
                .build();
        System.out.println("==================== saved "+savedEntity);

        Project domainProject = Project.builder()
                .name("New project")
                .description("Project description")
                .id(1L)
                .build();


        when(projectMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(savedEntity);
        when(projectMapper.toDomain(savedEntity)).thenReturn(domainProject);

        // when
        Long createId = Objects.requireNonNull(projectService.create(request).getBody()).getId();
        System.out.println("==================== createId "+createId);
        // then check
        Assertions.assertEquals(1L, createId);
    }

    @Test
    void given_valid_id_return_a_project() throws NotFoundException {
        Long expectedId = 1L;
        ProjectEntity givenEntity = ProjectEntity.builder()
                .description("Test project description")
                .name("Test project")
                .id(expectedId)
                .build();
        Project mockProject = Project.builder()
                .id(expectedId)
                .name("Test project")
                .description("Test project description")
                .build();
        when(projectRepository.findById(expectedId)).thenReturn(Optional.of(givenEntity));
        when(projectMapper.toDomain(givenEntity))
                .thenReturn(mockProject);

        ResponseEntity<Project> response = projectService.getById(expectedId);

        Assertions.assertEquals(expectedId, Objects.requireNonNull(response.getBody()).getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockProject, response.getBody());

    }
    @Test
    void get_invalid_id_throw_not_found_exception(){
        Long projectId = 999L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()->projectService.getById(projectId));
    }
    @Test
    void getAll_return_project_list(){
        // given
        Pageable pageable = Pageable.unpaged();
        ProjectEntity entity1 = ProjectEntity.builder()
                .id(1L)
                .name("Project 1")
                .description("Project 1 description")
                .build();
        ProjectEntity entity2 = ProjectEntity.builder()
                .id(2L)
                .name("Project 2")
                .description("Project 2 description")
                .build();

        List<ProjectEntity> entities = List.of(entity1, entity2);

        Project mockEntity1 = Project.builder()
                .id(1L)
                .name("Project 1")
                .description("Project 1 description")
                .build();

        Project mockEntity2 = Project.builder()
                .id(2L)
                .name("Project 2")
                .description("Project 2 description")
                .build();

        //List<Project> mockEntities = List.of(mockEntity1, mockEntity1);

        when(projectRepository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(projectMapper.toDomain(entity1)).thenReturn(mockEntity1);
        when(projectMapper.toDomain(entity2)).thenReturn(mockEntity2);

        List<Project> response = Objects.requireNonNull(projectService.getAll(pageable).getBody());

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(1L, response.get(0).getId());
        Assertions.assertEquals("Project 1", response.get(0).getName());
        Assertions.assertEquals("Project 1 description", response.get(0).getDescription());
        Assertions.assertEquals(2L, response.get(1).getId());
        Assertions.assertEquals("Project 2", response.get(1).getName());
        Assertions.assertEquals("Project 2 description", response.get(1).getDescription());

    }

    @Test
    void updateProject_updates_name_when__exists(){
        // given
        Long projectId = 1L;
        String newName = "Project 1 new name";
        UpdateProjectRequest updateRequest = UpdateProjectRequest.builder()
                .name(newName)
                .build();
        ProjectEntity oldEntity = ProjectEntity.builder()
                .id(projectId)
                .description("Old description")
                .name("Project 1 old name")
                .build();
        ProjectEntity mockEntity = ProjectEntity.builder()
                .id(projectId)
                .name(newName)
                .description("Old description")
                .build();


        when(projectRepository.findById(projectId)).thenReturn(Optional.of(oldEntity));
        when(projectMapper.updateRequestToEntity(updateRequest, oldEntity)).thenReturn(mockEntity);

        projectService.update(projectId, updateRequest);

        //ArgumentCaptor<ProjectEntity> captor = ArgumentCaptor.forClass(ProjectEntity.class);
        Assertions.assertEquals(newName, mockEntity.getName());
        Mockito.verify(projectRepository).save(mockEntity);
    }

    @Test
    void updateProject_updates_description_when__exists(){
        // given
        Long projectId = 1L;
        String newDescription = "Project 1 new description";
        UpdateProjectRequest updateRequest = UpdateProjectRequest.builder()
                .description(newDescription)
                .build();
        ProjectEntity oldEntity = ProjectEntity.builder()
                .id(projectId)
                .description("Old description")
                .name("Project 1 name")
                .build();
        ProjectEntity mockEntity = ProjectEntity.builder()
                .id(projectId)
                .name("Project 1 name")
                .description(newDescription)
                .build();


        when(projectRepository.findById(projectId)).thenReturn(Optional.of(oldEntity));
        when(projectMapper.updateRequestToEntity(updateRequest, oldEntity)).thenReturn(mockEntity);

        projectService.update(projectId, updateRequest);

        //ArgumentCaptor<ProjectEntity> captor = ArgumentCaptor.forClass(ProjectEntity.class);
        Assertions.assertEquals(newDescription, mockEntity.getDescription());
        Mockito.verify(projectRepository).save(mockEntity);
    }

    @Test
    void updateProject_updates_name_description_when__exists(){
        // given
        Long projectId = 1L;
        String newDescription = "Project 1 new description";
        String newName = "Project 1 new name";
        UpdateProjectRequest updateRequest = UpdateProjectRequest.builder()
                .description(newDescription)
                .name(newName)
                .build();
        ProjectEntity oldEntity = ProjectEntity.builder()
                .id(projectId)
                .description("Old description")
                .name("Project 1 old name")
                .build();
        ProjectEntity mockEntity = ProjectEntity.builder()
                .id(projectId)
                .name(newName)
                .description(newDescription)
                .build();
        Project domainProject = Project.builder()
                .id(projectId)
                .name(newName)
                .description(newDescription)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(oldEntity));
        when(projectMapper.updateRequestToEntity(updateRequest, oldEntity)).thenReturn(mockEntity);
        when(projectRepository.save(mockEntity)).thenReturn(mockEntity);
        when(projectMapper.toDomain(mockEntity)).thenReturn(domainProject);

        Project response = Objects.requireNonNull(projectService.update(projectId, updateRequest).getBody());

        //ArgumentCaptor<ProjectEntity> captor = ArgumentCaptor.forClass(ProjectEntity.class);
        Assertions.assertEquals(domainProject.getDescription(), response.getDescription());
        Assertions.assertEquals(domainProject.getName(), response.getName());
        Mockito.verify(projectRepository).save(mockEntity);
    }
    @Test
    void updateProject_throw_NotFoundException_when_NotFound(){
        Long projectId = 999L;
        String name = "Project 999";
        String description = "Project 999 description";
        UpdateProjectRequest request = UpdateProjectRequest.builder()
                .name(name)
                .description(description)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(NotFoundException.class, ()->projectService.update(projectId, request));
    }

    @Test
    void deletedProject_project_when_exists(){
        Long projectId = 1L;
        ProjectEntity entity = ProjectEntity.builder()
                .id(projectId)
                .name("Test delete")
                .description("Test delete description")
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(entity));
        projectService.delete(projectId);

        Mockito.verify(projectRepository).deleteById(projectId);
    }
}
