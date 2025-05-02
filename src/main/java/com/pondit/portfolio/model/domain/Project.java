package com.pondit.portfolio.model.domain;

public class Project {
    private final String name; // required
    private final String description; // required

    // creation an object of Project
    public Project(String name, String description) {
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
