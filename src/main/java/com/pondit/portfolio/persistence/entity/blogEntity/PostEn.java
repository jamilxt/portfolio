package com.pondit.portfolio.persistence.entity.blogEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "post_en")
public class PostEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String content;

    private String author;
    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    public String getPreview() {
        if (content == null) return "";

        // Convert literal "\n" strings to real newlines
        String processedContent = content.replace("\\n", "\n");

        String[] lines = processedContent.split("\\r?\\n");
        String preview = Arrays.stream(lines)
                .limit(5)
                .collect(Collectors.joining("\n"));

        return preview + (lines.length > 5 ? "\n..." : "");
    }


}
