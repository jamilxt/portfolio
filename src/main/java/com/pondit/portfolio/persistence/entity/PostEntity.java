package com.pondit.portfolio.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
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

    @PrePersist
    public void setDefaults() {
        if (published == null) {
            published = true;
        }
        if (publishedAt == null && Boolean.TRUE.equals(published)) {
            publishedAt = LocalDateTime.now();
        }
    }
}
