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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Resource", description = "API for managing posts")
@RestController
@RequestMapping("api/posts")
public class PostRestController {
    @Autowired
    PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getAllPosts(@ParameterObject Pageable pageable) {
        return postService.getAllPosts(pageable);
    }
    @Operation(summary = "Create a new post")
    @PostMapping
    public Long createPost(@RequestBody CreatePostRequest request){
        return postService.create(request);
    }
    @Operation(summary = "Get a post by id")
    @GetMapping("{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) throws NotFoundException {
        Post post = postService.getById(id);
        return ResponseEntity.ok(post);
    }
    @Operation(summary = "Update a post by id")
    @PutMapping("{id}")
    public void updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) throws NotFoundException {
        postService.update(id, request);
    }

    @Operation(summary = "Delete a post by id")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable Long id) throws NotFoundException {
        postService.delete(id);
    }

}
