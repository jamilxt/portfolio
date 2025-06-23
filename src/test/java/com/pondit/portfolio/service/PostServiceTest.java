package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Unit tests for PostService using JUnit 5 and Mockito.
 * This test class verifies service-level logic in isolation from the database and mapping layer.
 */

@ExtendWith(MockitoExtension.class) // Enables Mockito support in JUnit 5
class PostServiceTest {

    @InjectMocks
    private PostService postService; // Class under test. it injects all dependency

    @Mock
    private PostRepository postRepository; // Mocked data access layer

    @Mock
    private PostMapper postMapper;  // Mocked mapping layer (PostEntity ↔ Post)

    private PostEntity sampleEntity;
    private Post sampleDomain;


    /**
     * Initializes test data before each test case.
     * Sets up a dummy PostEntity and its corresponding Post domain object.
     */
    @BeforeEach
    void setUp() {
        sampleEntity = new PostEntity();
        sampleEntity.setId(1L);
        sampleEntity.setTitle("Title");
        sampleEntity.setContent("Content");
        sampleEntity.setSlug("title");
        sampleEntity.setPublished(true);
        sampleEntity.setPublishedAt(LocalDateTime.now());

        sampleDomain = new Post(
                1L,
                "Title",
                "Content",
                "title",
                true,
                sampleEntity.getPublishedAt());
    }

    /**
     * Test getAllPosts() returns a list of posts correctly.
     */
    @Test
    void getAllPosts_returns_post_list() {
        Pageable pageable = PageRequest.of(0, 10);

        // Mock repository to return one sample entity, when findAll are called
        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(sampleEntity)));
        // Mock mapper to convert entity to domain, when call
        when(postMapper.entryToDomain(sampleEntity)).thenReturn(sampleDomain);

        List<Post> posts = postService.getAllPosts(pageable);  // Call the service method

        // Verify the result
        assertEquals(1, posts.size());
        assertEquals("Title", posts.get(0).getTitle());

        // Ensure dependencies were called
        verify(postRepository).findAll(pageable);
        verify(postMapper).entryToDomain(sampleEntity);
    }

    /**
     * Test createPost() saves the post and returns its ID.
     */
    @Test
    void createPost_saves_and_returns_id() {
        CreatePostRequest request = new CreatePostRequest("Title", "Content", "title", true, LocalDateTime.now());

        when(postMapper.createRequestToEntity(request)).thenReturn(sampleEntity);
        when(postRepository.save(sampleEntity)).thenReturn(sampleEntity);

        Long id = postService.createPost(request);

        assertEquals(1L, id);
        verify(postRepository).save(sampleEntity);
    }

    /**
     * Test getById() returns the correct post when it exists.
     */
    @Test
    void getById_returns_post_when_exists() throws NotFoundException {
        when(postRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(postMapper.entryToDomain(sampleEntity)).thenReturn(sampleDomain);

        Post post = postService.getById(1L);

        assertEquals("Title", post.getTitle());
        verify(postMapper).entryToDomain(sampleEntity);
    }

    /**
     * Test getById() throws NotFoundException when post does not exist.
     */
    @Test
    void getById_throws_NotFoundException_when_not_found() {
        // it return emplty
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // it Throws exception
        assertThrows(NotFoundException.class, () -> postService.getById(1L));
    }


    /**
     * Test update() updates a post successfully when it exists.
     */
    @Test
    void updatePost_updates_when_exists() throws NotFoundException {
        UpdatePostRequest request = new UpdatePostRequest("Updated", "Updated Content", "updated-slug", true, LocalDateTime.now());
        PostEntity updated = new PostEntity();
        updated.setId(1L);
        updated.setTitle("Updated");

        // Simulate fetching, mapping, and saving
        when(postRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(postMapper.updatePostEntity(sampleEntity, request)).thenReturn(updated);

        postService.update(1L, request);

        verify(postRepository).save(updated);
    }

    /**
     * Test update() throws NotFoundException when the post does not exist.
     */
    @Test
    void updatePost_throws_NotFoundException_when_not_found() {
        UpdatePostRequest request = new UpdatePostRequest("Updated", "Updated Content", "updated-slug", true, LocalDateTime.now());
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.update(1L, request));
    }


    /**
     * Test delete() removes the post when it exists.
     */
    @Test
    void deletePost_deletes_when_found() throws NotFoundException {
        when(postRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));

        postService.delete(1L);

        verify(postRepository).deleteById(1L);
    }

    /**
     * Test delete() throws NotFoundException when the post does not exist.
     */
    @Test
    void deletePost_throws_NotFoundException_when_not_found() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.delete(1L));
    }
}
