package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    public Post entityToDomain(PostEntity entity) {
        Post post = new Post();
        BeanUtils.copyProperties(entity, post);
        return post;
    }

    public PostEntity createRequestToEntity(CreatePostRequest post) {
        PostEntity entity = new PostEntity();
        BeanUtils.copyProperties(post, entity);
        entity.setPublishedAt(LocalDateTime.now());
        return entity;
    }

    public PostEntity updateRequestToEntity(UpdatePostRequest post,PostEntity entity) {
        entity.setContent(post.content());
        return entity;
    }
}
