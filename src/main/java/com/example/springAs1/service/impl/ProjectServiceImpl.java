package com.example.springAs1.service.impl;

import com.example.springAs1.exception.custom.NotFoundException;
import com.example.springAs1.mapper.ProjectMapper;
import com.example.springAs1.model.domain.Project;
import com.example.springAs1.model.dto.CreateProjectRequest;
import com.example.springAs1.model.dto.UpdateProjectRequest;
import com.example.springAs1.persistence.entity.ProjectEntity;
import com.example.springAs1.persistence.repository.ProjectRepository;
import com.example.springAs1.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepo;
    private final ProjectMapper projectMapper;

    @Override
    public ResponseEntity<List<Project>> getAll(Pageable pageable) {
        List<ProjectEntity> entities = projectRepo.findAll(pageable).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(entities.stream().map(projectMapper::toDomain).toList());
    }

    @Override
    public ResponseEntity<Project> create(CreateProjectRequest request) {
        ProjectEntity entity = projectMapper.createRequestToEntity(request);
        ProjectEntity saved = projectRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMapper.toDomain(saved));
    }

    @Override
    public ResponseEntity<Project> getById(Long id) throws NotFoundException {
        ProjectEntity entity = projectRepo.findById(id).orElseThrow(()->new NotFoundException("Not found"));
        return ResponseEntity.status(HttpStatus.OK).body(projectMapper.toDomain(entity));
    }

    @Override
    public ResponseEntity<Project> update(Long id, UpdateProjectRequest request) throws NotFoundException {
        ProjectEntity entity = projectRepo.findById(id).orElseThrow(()-> new NotFoundException("Not found: "+ id));


        ProjectEntity updateEntity = projectMapper.updateRequestToEntity(request, entity);
        ProjectEntity saved = projectRepo.save(updateEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectMapper.toDomain(saved));
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws NotFoundException {
        ProjectEntity entity = projectRepo.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
        projectRepo.deleteById(entity.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successful!");
    }
}
