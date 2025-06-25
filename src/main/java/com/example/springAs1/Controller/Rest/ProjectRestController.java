package com.example.springAs1.Controller.Rest;

import com.example.springAs1.model.dto.CreateProjectRequest;
import com.example.springAs1.model.dto.UpdateProjectRequest;
import com.example.springAs1.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project Resource", description = "APIs for creating, updating, retrieving, and deleting project")
@RestController
@RequestMapping("api/projects")

public class ProjectRestController {
    @Autowired
    private ProjectService service;
    @Operation(summary = "Create a new project", description = "name, and description for create a new project")
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody CreateProjectRequest request){
        return service.create(request);
    }

    @Operation(summary = "Get a project by ID(Long)")
    @GetMapping("{id}")
    public ResponseEntity<?> getProjectByID(@PathVariable Long id){
        return service.getById(id);
    }

    @Operation(summary = "Get all projects")
    @GetMapping
    public ResponseEntity<?> getAllProjects(@ParameterObject Pageable pageable){
        return service.getAll(pageable);
    }
    @Operation(summary = "Update a project")
    @PutMapping("{id}")
    public ResponseEntity<?> updateAProjectById(@PathVariable Long id,
                                                @RequestBody UpdateProjectRequest request){
        return service.update(id, request);
    }

    @Operation(summary = "delete a project")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletedAPost(@PathVariable Long id){
        return service.delete(id);
    }

}
