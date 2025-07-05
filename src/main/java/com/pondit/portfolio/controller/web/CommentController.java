package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.model.domain.Comment;
import com.pondit.portfolio.model.dto.CreateCommentRequest;
import com.pondit.portfolio.model.dto.UpdateCommentRequest;
import com.pondit.portfolio.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Create a new comment for a post")
    @PostMapping
    public Long createComment(@RequestBody CreateCommentRequest request) {
        return commentService.createComment(request);
    }

    @Operation(summary = "Get all comments for a post")
    @GetMapping
    public List<Comment> getAllComment() {
        return commentService.getAllComments();
    }

    @Operation(summary = "Delete a comment by id")
    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable Long id)throws NotFoundException {
        commentService.deleteComment(id);
    }

    @Operation(summary = "Get a comment by id")
    @GetMapping("{id}")
    public Comment getComment(@PathVariable Long id) throws  NotFoundException {
        return commentService.getCommentById(id);
    }

    @Operation(summary = "Update a comment by id")
    @PutMapping("{id}")
    public void updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) throws   NotFoundException
    {
        commentService.updateComment(id, request);
    }

}
