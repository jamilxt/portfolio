package com.pondit.portfolio.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "post", uniqueConstraints = {
        @UniqueConstraint(columnNames = "Intro")
})
public class PostEntity {
    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @Setter
    private String title;

    @Setter
    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;

    @Setter
    private String slug;

    @Setter
    @Column(name = "is_published")
    private Boolean published;

    @Setter
    private LocalDateTime publishedAt;

}
