package com.pondit.portfolio.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotBlank(message = "Content is mandatory")
        @Size(min = 10, message = "Content must be at least 10 characters long")
        String content,
        boolean published
) {
}