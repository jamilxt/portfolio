package com.pondit.portfolio.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String slug;

    @Column(name = "is_published")
    private Boolean published;

    private LocalDateTime publishedAt;
}

