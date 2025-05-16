package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.service.ProjectService;
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

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public Project createProject(@RequestBody CreateProjectRequest request) {
        // TODO: validate input
        return projectService.createProject(request);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project;
        try {
            project = projectService.getProjectById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(project);
    }

}
