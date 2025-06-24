package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getAll(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAll(pageable).getContent();
        return entityList.stream().map(postMapper::entityToDomain).toList();
    }

    public Long create(CreatePostRequest request) {
        var entityToSave = postMapper.createRequestToEntity(request);
        var savedEntity = postRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public Post getById(Long id) throws NotFoundException {
        var postEntity = this.findEntityById(id);
        return postMapper.entityToDomain(postEntity);
    }

    public void update(Long id, UpdatePostRequest request) throws NotFoundException {
        var postEntity = this.findEntityById(id);
        var updatedEntity = postMapper.updateRequestToEntity(request, postEntity);
        postRepository.save(updatedEntity);
    }

    public void delete(Long id) throws NotFoundException {
        this.findEntityById(id);
        postRepository.deleteById(id);
    }

    private PostEntity findEntityById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }
}
