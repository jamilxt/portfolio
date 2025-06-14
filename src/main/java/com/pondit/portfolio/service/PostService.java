package com.pondit.portfolio.service;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getAllPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAll(pageable).getContent();
        return entityList.stream().map(postEntity -> {
            // map entity to domain object
            Long entityId = postEntity.getId();
            String entityTitle = postEntity.getTitle();
            String entityContent = postEntity.getContent();
            String entitySlug = postEntity.getSlug();
            Boolean entityPublished = postEntity.getPublished();
            LocalDateTime entityPublishedAt = postEntity.getPublishedAt();

            Post post = new Post();
            post.setId(entityId);
            post.setTitle(entityTitle);
            post.setContent(entityContent);
            post.setSlug(entitySlug);
            post.setPublished(entityPublished);
            post.setPublishedAt(entityPublishedAt);
            return post;
        }).toList();
    }
}
