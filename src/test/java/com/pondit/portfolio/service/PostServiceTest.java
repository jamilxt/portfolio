package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper PostMapper;

    @Test
    void testCreatePost_setsPublishedAt_whenPublishedIsTrue() {
        // Arrange
        CreatePostRequest request = new CreatePostRequest("Title", "Content", "slug", true);
        PostEntity entityToSave = new PostEntity();
        entityToSave.setTitle("Title");
        entityToSave.setContent("Content");
        entityToSave.setSlug("slug");
        entityToSave.setPublished(true);

        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);

        when(postMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(entityToSave)).thenReturn(savedEntity);

        // Act
        Long postId = postService.create(request);

        // Assert
        assertEquals(1L, postId);
        assertNotNull(entityToSave.getPublishedAt());
        verify(postRepository).save(entityToSave);
    }

    @Test
    void testGetAllPosts_returnsMappedPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        PostEntity postEntity = new PostEntity();
        List<PostEntity> entities = List.of(postEntity);
        Post post = new Post(); // domain object

        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(postMapper.entityToDomain(postEntity)).thenReturn(post);

        List<Post> result = postService.getAllPosts(pageable);

        assertEquals(1, result.size());
        assertEquals(post, result.get(0));
    }

    @Test
    void testGetById_returnsMappedPost() throws NotFoundException {
        Long postId = 1L;
        PostEntity entity = new PostEntity();
        Post post = new Post();

        when(postRepository.findById(postId)).thenReturn(Optional.of(entity));
        when(postMapper.entityToDomain(entity)).thenReturn(post);

        Post result = postService.getById(postId);
        assertEquals(post, result);
    }

    @Test
    void testDelete_existingPost_callsDeleteById() throws NotFoundException {
        Long postId = 1L;
        PostEntity entity = new PostEntity();

        when(postRepository.findById(postId)).thenReturn(Optional.of(entity));

        postService.delete(postId);

        verify(postRepository).deleteById(postId);
    }
}


