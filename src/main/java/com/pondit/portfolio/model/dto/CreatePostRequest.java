package com.pondit.portfolio.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "Title is mandatory")
        @Size(min = 5, message = "Title must be at least 5 characters long")
        String title,

        @NotBlank(message = "Content is mandatory")
        @Size(min = 10, message = "Content must be at least 10 characters long")
        String content,

        @NotNull(message = "Published status is required")
        Boolean published
) {}