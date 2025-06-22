package com.pondit.portfolio.model.dto;

import java.time.LocalDateTime;

/**
 * Request payload for updating an existing Post.
 * Assumes the post already exists and is being updated completely.
 * We also use PUT(full updates) not PATCH(partial updates)
 */
public record UpdatePostRequest(
        String title,
        String content,
        String slug,
        Boolean published,
        LocalDateTime publishedAt // We take time from user when it will be post or by default it take post timw
) {}