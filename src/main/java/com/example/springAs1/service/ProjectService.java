package com.example.springAs1.service;

import com.example.springAs1.model.domain.Project;
import com.example.springAs1.model.dto.CreateProjectRequest;
import com.example.springAs1.model.dto.UpdateProjectRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    ResponseEntity<List<Project>> getAll(Pageable pageable);
    ResponseEntity<Project> create(CreateProjectRequest request);
    ResponseEntity<Project> getById(Long id);
    ResponseEntity<Project> update(Long id, UpdateProjectRequest request);
    ResponseEntity<?> delete(Long id);
}
