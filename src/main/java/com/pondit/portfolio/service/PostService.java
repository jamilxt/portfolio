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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper; //  // Mapper to convert Entity → Domain model

    /**
     * Fetch all posts using pagination and convert each PostEntity to Post domain model
     *
     * @param pageable - Spring Data pagination info (page number, size, sort)
     * @return List<Post> - list of domain-level post objects
     */

    public List<Post> getAllPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAll(pageable).getContent();

        // 🔁 Convert each PostEntity to domain(post) using the mapper
        return entityList.stream()
                .map(postMapper::entryToDomain).toList();
    }

    public Long createPost(CreatePostRequest request) {
        var entityToSave = postMapper.createRequestToEntity(request);
        var savedEntity = postRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public Post getById(Long id) throws NotFoundException {
        var postEntity = this.findById(id);
        return postMapper.entryToDomain(postEntity);
    }

    public void update(Long id, UpdatePostRequest request) throws NotFoundException {
        var postEntity = this.findById(id);
        var updatedPostEntity = postMapper.updatePostEntity(postEntity,request);
        postRepository.save(updatedPostEntity);
    }

    public  void delete(Long id) throws NotFoundException {
        this.findById(id);
        postRepository.deleteById(id);
    }

    private PostEntity findById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("post not found"));
    }


}
