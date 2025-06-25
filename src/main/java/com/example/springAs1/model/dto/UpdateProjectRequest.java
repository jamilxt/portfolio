package com.example.springAs1.model.dto;

import lombok.Builder;


@Builder
public record UpdateProjectRequest(String description, String name) {
}
