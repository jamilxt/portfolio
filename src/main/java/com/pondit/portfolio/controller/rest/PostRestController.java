package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Resource", description = "API for managing posts")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/posts")
public class PostRestController {
    private final PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getAllPosts(@ParameterObject Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public void createPost(CreatePostRequest createPostRequest) {
       postService.create(createPostRequest);
    }

    @Operation(summary = "Update a post by id")
    @PutMapping("{id}")
    public void updateProject(@PathVariable Long id, @RequestBody UpdatePostRequest request) throws NotFoundException {
        postService.update(id, request);
    }

    @Operation(summary = "Delete a post by id")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable Long id) throws NotFoundException {
        postService.delete(id);
    }

    @Operation(summary = "Get a post by id")
    @GetMapping("{id}")
    public Post getPostById(@PathVariable Long id) throws NotFoundException {
        return postService.getById(id);
    }
}
