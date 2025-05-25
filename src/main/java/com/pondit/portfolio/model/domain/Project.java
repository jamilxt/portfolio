package com.pondit.portfolio.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Getter
    private Long id;

    @Getter
    private String name; // required

    @Getter
    private String description; // required
}
