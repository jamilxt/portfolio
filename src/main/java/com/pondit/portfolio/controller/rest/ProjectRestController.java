package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    List<Project> projects = new ArrayList<>();

    @GetMapping
    public List<Project> getAllProjects() {
        return projects;
    }

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
