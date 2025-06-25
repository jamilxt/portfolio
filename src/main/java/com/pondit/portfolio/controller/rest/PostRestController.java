package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Resource", description = "API for managing posts")
@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> post(@ParameterObject Pageable pageable) {
        return postService.getAllPost(pageable);
    }

    @Operation(summary = "Creat posts")
    @PostMapping
    public void create(@RequestBody CreatePostRequest createPostRequest) {
        postService.createPost(createPostRequest);
    }

    @Operation(summary = "Update posts")
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest) throws NotFoundException {
        postService.updatePost(id, updatePostRequest);
    }

    @Operation(summary = "Delete posts")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        postService.deletePost(id);
    }

}
