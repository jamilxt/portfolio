package com.pondit.portfolio.controller;

import com.pondit.portfolio.model.domain.Project;
import com.pondit.portfolio.model.dto.CreateProjectRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectRestController {
    List<Project> projects = new ArrayList<>();

    @GetMapping("/api/projects")
    public List<Project> getAllProjects() {
        return projects;
    }

    @PostMapping("/api/projects")
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
