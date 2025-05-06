package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Project Resource", description = "API for managing projects")
@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    List<Project> projects = new ArrayList<>();

    @Tag(name = "Get All Projects", description = "Get all projects")
    @GetMapping
    public List<Project> getAllProjects() {
        return projects;
    }

    @Tag(name = "Create Project", description = "Create a new project")
    @PostMapping
    public Project createProject(@RequestBody CreateProjectRequest request) {
        // TODO: validate input
        // TODO: business logic
        // TODO: save to database/file or network call

        String name = request.getName();
        String description = request.getDescription();

        Project project = new Project(name, description);
        projects.add(project);

        return project;
    }

}
