package com.pondit.portfolio.model.dto;

public record CreatePostRequest(String title, String content, String slug, Boolean published) {
}
