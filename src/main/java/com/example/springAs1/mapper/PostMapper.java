package com.example.springAs1.mapper;

import com.example.springAs1.model.domain.Post;
import com.example.springAs1.model.dto.CreatePostRequest;
import com.example.springAs1.model.dto.UpdatePostRequest;

import com.example.springAs1.persistence.entity.PostEntity;

import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Builder
public class PostMapper {
    public Post toDomain(PostEntity entity){
        return Post.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .slug(entity.getSlug())
                .published(entity.getPublished())
                .publishedAt(entity.getPublishedAt())
                .build();
    }

    public PostEntity createPostRequestToEntity(CreatePostRequest request){
        return PostEntity.builder()
                .title(request.title() == null? "Untitled":request.title())
                .content(request.content() == null
                        ? "No content": request.content())
                .slug(request.slug() == null
                        ? "No slug":request.slug())
                .published(request.published() != null
                        ? request.published(): false)
                .publishedAt(request.published() != null && request.published()
                        ? LocalDateTime.now() : null)
                .build();
    }

    public PostEntity updatePostRequestToEntity(UpdatePostRequest request, PostEntity entity){
        boolean wasPublished = Boolean.TRUE.equals(entity.getPublished());
        boolean newPublished = request.published() != null ? request.published() : entity.getPublished();
        boolean willPublished = Boolean.TRUE.equals(newPublished);

        return PostEntity.builder()
                .id(entity.getId())
                .title(request.title() == null? entity.getTitle() :request.title())
                .content(request.content() == null ? entity.getContent(): request.content())
                .slug(request.slug() == null ? entity.getSlug() : request.slug())
                .published(newPublished)
                .publishedAt(
                        ( !wasPublished && willPublished )?
                                LocalDateTime.now() :
                                    entity.getPublishedAt()
                )
                .build();
    }
}
