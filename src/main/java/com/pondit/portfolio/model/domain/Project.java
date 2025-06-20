package com.pondit.portfolio.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id; // 1
    private String name; // required
    private String description; // required
}
