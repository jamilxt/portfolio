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
    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;

    @Setter
    @Column(unique = true, nullable = false)
    private String slug;

    @Setter
    @Column(name = "is_published")
    private Boolean published;

    @Setter
    private LocalDateTime publishedAt;
    @Setter
    @Column(length = 400)
    private String intro;

}
