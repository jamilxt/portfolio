package com.pondit.portfolio.model.domain;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private Boolean published;
    private LocalDateTime publishedAt;
    private String intro;

}
