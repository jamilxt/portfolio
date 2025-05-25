package com.pondit.portfolio.model.dto;

public final class CreateProjectRequest {
    private final String name;
    private final String description;

    public CreateProjectRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
