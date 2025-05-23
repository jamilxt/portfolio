package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.exception.NotFoundException;
import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Resource", description = "API for managing projects")
@RestController
@RequestMapping("api/projects")
public class ProjectRestController {
    @Autowired
    ProjectService projectService;

    @Operation(summary = "Get all projects")
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Operation(summary = "Create a new project")
    @PostMapping
    public Project createProject(@RequestBody CreateProjectRequest request) {
        // TODO: validate input
        return projectService.createProject(request);
    }

    @Operation(summary = "Get a project by id")
    @GetMapping("{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) throws NotFoundException {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Update a project by id")
    @PutMapping("{id}")
    public void updateProject(@PathVariable Long id, @RequestBody UpdateProjectRequest request) throws NotFoundException {
            projectService.updateProject(id, request);
    }

    @Operation(summary = "Delete a project by id")
    @DeleteMapping("{id}")
    public void deleteProject(@PathVariable Long id) throws NotFoundException {
            projectService.deleteProject(id);
    }
}
