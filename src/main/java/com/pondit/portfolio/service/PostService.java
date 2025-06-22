package com.pondit.portfolio.service;

import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
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
}
