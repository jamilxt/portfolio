package com.pondit.portfolio.controller.rest;

import com.pondit.portfolio.model.domain.Post;
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
@RequestMapping("api/posts")
public class PostRestController {
    @Autowired
    PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getAllPosts(@ParameterObject Pageable pageable) {
        return postService.getAllPosts(pageable);
    }
}
