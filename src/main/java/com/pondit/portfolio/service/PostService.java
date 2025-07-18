package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import com.pondit.portfolio.utils.PostUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getAllPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAll(pageable).getContent();
        return entityList.stream().map(postMapper::entityToDomain).toList();
    }

    public List<Post> getAllPublishedPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAllByPublishedIsTrue(pageable).getContent();
        return entityList.stream().map(postMapper::entityToDomain).toList();
    }

    public List<Post> getLatestPublishedPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findTopByPublishedIsTrueOrderByPublishedAtDesc(pageable);
        return entityList.stream().map(postMapper::entityToDomain).toList();
    }

    public Long create(CreatePostRequest request) {
        var entityToSave = postMapper.createRequestToEntity(request);
        entityToSave.setIntro(generateIntro(entityToSave.getContent()));

        var title = request.title();

        PostUtils postUtils = new PostUtils();
        String slug = postUtils.getUniqueSlug(title);

        while (postRepository.existsBySlug(slug)) {
            slug = postUtils.getUniqueSlug(title);
        }
        entityToSave.setSlug(slug);

        if (request.published()) {
            entityToSave.setPublishedAt(LocalDateTime.now());
        }

        var savedEntity = postRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public Post getById(Long id) throws NotFoundException {
        var postEntity = this.findEntityById(id);
        return postMapper.entityToDomain(postEntity);
    }

    public void update(Long id, UpdatePostRequest request) throws NotFoundException {
        PostEntity postEntity = this.findEntityById(id);
        PostEntity updatedPostEntity = postMapper.updateRequestToEntity(request, postEntity);
        updatedPostEntity.setIntro(generateIntro(updatedPostEntity.getContent()));

        if (request.published()) {
            updatedPostEntity.setPublishedAt(LocalDateTime.now());
        } else {
            updatedPostEntity.setPublishedAt(null);
        }

        postRepository.save(updatedPostEntity);
    }

    public void delete(Long id) throws NotFoundException {
        this.findEntityById(id);
        postRepository.deleteById(id);
    }


    private PostEntity findEntityById(Long id) throws  NotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    private String generateIntro(String content) {
        if (content == null || content.isBlank()) return "";
        int limit = 300;
        String suffix="...";
        return content.length() <= limit
                ? content
                : content.substring(0, limit) + suffix;
    }

}

