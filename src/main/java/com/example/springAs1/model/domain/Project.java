package com.example.springAs1.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {
    private Long id;
    private String name;
    private String description;
}
