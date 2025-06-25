package com.example.springAs1.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record UpdatePostRequest(String title, String content, String slug, Boolean published) {
}
