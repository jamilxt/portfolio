package com.pondit.portfolio.model.dto;

import java.time.LocalDateTime;

public record CreatePostRequest(String title, String content, String slug, Boolean published, LocalDateTime publishedAt) {
}