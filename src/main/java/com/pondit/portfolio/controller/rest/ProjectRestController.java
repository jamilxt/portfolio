package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import com.pondit.portfolio.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Resource", description = "API for managing projects")
@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    @Tag(name = "Get All Projects", description = "Get all projects")
    @GetMapping
    public List<Project> getAllProjects() {
        ProjectService projectService = new ProjectService();
        return projectService.getAllProjects();
    }

    @Tag(name = "Create Project", description = "Create a new project")
    @PostMapping
    public Project createProject(@RequestBody CreateProjectRequest request) {
        // TODO: validate input
        ProjectService projectService = new ProjectService();
        return projectService.createProject(request);
    }

}
