package com.pondit.portfolio.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post", uniqueConstraints = {
        @UniqueConstraint(columnNames = "slug")
})
public class PostEntity {
    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @Setter
    private String title;

    @Setter
    private String content;

    @Setter
    private String slug;

    @Setter
    @Column(name = "is_published")
    private Boolean published;

    @Setter
    private LocalDateTime publishedAt;
}
