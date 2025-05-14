package com.pondit.portfolio.model.domain;

public class Project {
    private Long id;
    private final String name; // required
    private final String description; // required

    // creation an object of Project
    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
