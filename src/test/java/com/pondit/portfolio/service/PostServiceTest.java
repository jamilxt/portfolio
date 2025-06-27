package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Test
    void getAll_returns_post_list(){
        //given
        Pageable pageable = Pageable.unpaged();
        PostEntity postEntity1 = new PostEntity();
        postEntity1.setId(1L);
        postEntity1.setTitle("Title 1");
        postEntity1.setContent("Content 1");
        postEntity1.setSlug("Slug 1");
        postEntity1.setPublished(true);
        postEntity1.setPublishedAt(LocalDateTime.of(2025, 6, 22, 14, 30));

        PostEntity postEntity2 = new PostEntity();
        postEntity2.setId(2L);
        postEntity2.setTitle("Title 2");
        postEntity2.setContent("Content 2");
        postEntity2.setSlug("Slug 2");
        postEntity2.setPublished(true);
        postEntity2.setPublishedAt(LocalDateTime.of(2025, 6, 22, 14, 30));

        List<PostEntity> postEntityList = List.of(postEntity1, postEntity2);

        //Mock the repository
        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(postEntityList));
        when(postMapper.entityToDomain(postEntity1)).thenReturn(new Post(1L, "Title 1", "Content 1", "Slug 1", true, LocalDateTime.of(2025, 6, 22, 14, 30)));
        when(postMapper.entityToDomain(postEntity2)).thenReturn(new Post(2L, "Title 2", "Content 2", "Slug 2", true, LocalDateTime.of(2025, 6, 22, 14, 30)));

        List<Post> result = postService.getAllPosts(pageable);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals("Title 1", result.get(0).getTitle());
        Assertions.assertEquals("Content 1", result.get(0).getContent());
        Assertions.assertEquals("Slug 1", result.get(0).getSlug());
        Assertions.assertEquals(true, result.get(0).getPublished());
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 22, 14, 30), result.get(0).getPublishedAt());
        Assertions.assertEquals(2L, result.get(1).getId());
        Assertions.assertEquals("Title 2", result.get(1).getTitle());
        Assertions.assertEquals("Content 2", result.get(1).getContent());
        Assertions.assertEquals("Slug 2", result.get(1).getSlug());
        Assertions.assertEquals(true, result.get(1).getPublished());
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 22, 14, 30), result.get(1).getPublishedAt());
    }

    @Test
    void createPost_saves_and_returns_(){
        CreatePostRequest request = new CreatePostRequest("New Post", "New Post Content", "new-post-slug");
        PostEntity entityToSave = new PostEntity();
        entityToSave.setTitle("New Post");
        entityToSave.setContent("New Content");
        entityToSave.setSlug("new-post-slug");

        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);
        savedEntity.setTitle("New Post");
        savedEntity.setContent("New Content");
        savedEntity.setSlug("new-post-slug");
        when(postMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(any(PostEntity.class))).thenReturn(savedEntity);

        Long createdId = postService.create(request);

        Assertions.assertEquals(1L, createdId);
    }

    @Test
    void given_valid_id_return_a_post() throws NotFoundException {
        // given/setup
        long expectedId = 1L;
        LocalDateTime expectedPublishedAt = LocalDateTime.of(2023, 6, 27, 10, 30);
        boolean expectedPublished = true;

        PostEntity postEntity = new PostEntity();
        postEntity.setId(expectedId);
        postEntity.setTitle("Test Post");
        postEntity.setContent("Test Content");
        postEntity.setSlug("test-post-slug");
        postEntity.setPublished(expectedPublished);
        postEntity.setPublishedAt(expectedPublishedAt);

        when(postRepository.findById(expectedId)).thenReturn(Optional.of(postEntity));
        when(postMapper.entityToDomain(postEntity)).
                thenReturn(new Post(expectedId, "Test Post", "Test Content", "test-post-slug", expectedPublished, expectedPublishedAt));

        // when
        Post post = postService.getById(expectedId);

        // then/verify
        Assertions.assertEquals(expectedId, post.getId());
        Assertions.assertEquals("Test Post", post.getTitle());
        Assertions.assertEquals("Test Content", post.getContent());
        Assertions.assertEquals("test-post-slug", post.getSlug());
        Assertions.assertEquals(expectedPublished, post.getPublished());
        Assertions.assertEquals(expectedPublishedAt, post.getPublishedAt());
    }
    @Test
    void given_invalid_id_throw_not_found_exception() {
        // given/setup
        long invalidId = 999L;
        when(postRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> postService.getById(invalidId));
    }

    @Test
    void updatePost_updates_content_slug_when__exists() throws NotFoundException {
        // given
        Long postId = 1L;
        String newContent = "Updated Description";
        String newSlug = "updated-slug";
        UpdatePostRequest request = new UpdatePostRequest(newContent, newSlug);

        PostEntity existingEntity = new PostEntity();
        existingEntity.setId(postId);
        existingEntity.setTitle("Test Post");
        existingEntity.setContent("Old Content");
        existingEntity.setSlug("old-slug");

        PostEntity updatedEntity = new PostEntity();
        updatedEntity.setId(postId);
        updatedEntity.setTitle("Test Post");
        updatedEntity.setContent(newContent);
        updatedEntity.setSlug(newSlug);

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingEntity));
        when(postMapper.updateRequestToEntity(request, existingEntity)).thenReturn(updatedEntity);

        // when
        postService.update(postId, request);

        // then
        Mockito.verify(postRepository).save(updatedEntity);
        Assertions.assertEquals(newContent, updatedEntity.getContent());
        Assertions.assertEquals(newSlug, updatedEntity.getSlug());
    }
    @Test
    void updatePost_throws_NotFoundException_when__not_found() {
        // given
        Long postId = 999L;
        UpdatePostRequest request = new UpdatePostRequest("Any Content", "any-slug");
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> postService.update(postId, request));
    }

    @Test
    void deletePost_deletes_when__exists() throws NotFoundException {
        // given
        Long postId = 1L;
        PostEntity entity = new PostEntity();
        entity.setId(postId);
        entity.setTitle("Test Post");
        entity.setContent("Test Content");
        entity.setSlug("test-slug");

        when(postRepository.findById(postId)).thenReturn(Optional.of(entity));

        // when
        postService.delete(postId);

        // then
        Mockito.verify(postRepository).deleteById(postId);
    }

    @Test
    void deletePost_throws_NotFoundException_when__not_found() {
        // given
        Long postId = 999L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> postService.delete(postId));
    }

}
