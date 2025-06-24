package com.pondit.portfolio.model.dto;

public record UpdatePostRequest(String title, String content, String slug, Boolean published) {
}
