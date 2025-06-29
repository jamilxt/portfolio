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


    public String getPreviewAsHtml() {
        if (content == null) return "";

        String[] lines = content.split("\\r?\\n");
        String preview = Arrays.stream(lines)
                .limit(5)
                .collect(Collectors.joining("<br>"));

        return preview + (lines.length > 5 ? "<br>..." : "");
    }

}
