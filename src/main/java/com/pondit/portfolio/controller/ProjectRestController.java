package com.pondit.portfolio.controller;

import com.pondit.portfolio.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectRestController {

    @GetMapping("/api/projects")
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        Project projectOne = new Project("Project One", "This is the description of project one.");
        Project projectTwo = new Project("Project Two", "This is the description of project two.");
        Project projectThree = new Project("Project Three", "This is the description of project three.");
        projects.add(projectOne);
        projects.add(projectTwo);
        projects.add(projectThree);
        return projects;
    }

}
