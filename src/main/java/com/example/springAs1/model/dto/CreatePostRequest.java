package com.example.springAs1.model.dto;

public record CreatePostRequest(String title, String content, String slug, Boolean published) {
}
