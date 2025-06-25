package com.example.springAs1.Controller.Rest;

import com.example.springAs1.model.dto.CreatePostRequest;
import com.example.springAs1.model.dto.UpdatePostRequest;
import com.example.springAs1.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post Resource", description = "APIs for creating, updating, retrieving, and deleting post")
@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostRestController {
    private  final PostService service;

    @Operation(summary = "Create a post")
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request){
        return service.create(request);
    }

    @Operation(summary = "Get a post")
    @GetMapping("{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        return service.get(id);
    }

    @Operation(summary = "Get all post")
    @GetMapping
    public ResponseEntity<?> getAllPost(@ParameterObject Pageable pageable){
        return service.getAll(pageable);
    }

    @Operation(summary = "Update a post ")
    @PutMapping("{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request){
        return service.update(id, request);
    }

    @Operation(summary = "Deleted a post")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        return service.delete(id);
    }

}
