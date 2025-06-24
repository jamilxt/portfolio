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
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Test
    void getAllPost_returns_post_list() {
        // given
        Pageable pageable = Pageable.unpaged();
        PostEntity entity1 = new PostEntity();
        entity1.setId(1L);
        entity1.setTitle("Post 1");
        entity1.setContent("Content 1");
        entity1.setSlug("post-1");
        entity1.setPublished(true);
        entity1.setPublishedAt(LocalDateTime.of(2025, 1, 1, 12, 0));

        PostEntity entity2 = new PostEntity();
        entity2.setId(2L);
        entity2.setTitle("Post 2");
        entity2.setContent("Content 2");
        entity2.setSlug("post-2");
        entity2.setPublished(false);
        entity2.setPublishedAt(null);

        List<PostEntity> entities = List.of(entity1, entity2);

        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(postMapper.entityToDomain(entity1))
                .thenReturn(new Post(1L, "Post 1", "Content 1", "post-1", true, LocalDateTime.of(2025, 1, 1, 12, 0)));
        when(postMapper.entityToDomain(entity2))
                .thenReturn(new Post(2L, "Post 2", "Content 2", "post-2", false, null));

        // when
        List<Post> result = postService.getAllPost(pageable);

        // then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals("Post 1", result.get(0).getTitle());
        Assertions.assertEquals("Content 1", result.get(0).getContent());
        Assertions.assertEquals("post-1", result.get(0).getSlug());
        Assertions.assertTrue(result.get(0).getPublished());
        Assertions.assertEquals(LocalDateTime.of(2025, 1, 1, 12, 0), result.get(0).getPublishedAt());

        Assertions.assertEquals(2L, result.get(1).getId());
        Assertions.assertEquals("Post 2", result.get(1).getTitle());
        Assertions.assertEquals("Content 2", result.get(1).getContent());
        Assertions.assertEquals("post-2", result.get(1).getSlug());
        Assertions.assertFalse(result.get(1).getPublished());
        Assertions.assertNull(result.get(1).getPublishedAt());
    }

    @Test
    void createPost_saves_post() {
        // given
        CreatePostRequest request = new CreatePostRequest("Post 1", "Content 1", "post-1", true, LocalDateTime.of(2025, 1, 1, 12, 0));
        PostEntity entityToSave = new PostEntity();
        entityToSave.setTitle("New Post");
        entityToSave.setContent("New Content");

        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);
        savedEntity.setTitle("New Post");
        savedEntity.setContent("New Content");
        savedEntity.setSlug("new-post");
        savedEntity.setPublished(false);
        savedEntity.setPublishedAt(null);

        when(postMapper.domainToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(any(PostEntity.class))).thenReturn(savedEntity);

        // when
        postService.createPost(request);

        // then
        Mockito.verify(postRepository).save(entityToSave);
    }

    @Test
    void updatePost_updates_content_when_exists() throws NotFoundException {
        // given
        Long postId = 1L;
        String updatedContent = "Updated Content";
        UpdatePostRequest request = new UpdatePostRequest(updatedContent);

        PostEntity existingEntity = new PostEntity();
        existingEntity.setId(postId);
        existingEntity.setTitle("Existing Title");
        existingEntity.setContent("Updated Content");


        when(postRepository.findById(postId)).thenReturn(Optional.of(existingEntity));
        when(postMapper.updateRequestToEntity(request, existingEntity)).thenReturn(existingEntity);

        // when
        postService.updatePost(postId, request);

        // then
        Assertions.assertEquals(updatedContent, existingEntity.getContent());
        Mockito.verify(postRepository).save(existingEntity);
    }

    @Test
    void updatePost_throws_NotFoundException_when_post_not_found() {
        // given
        Long postId = 999L;
        UpdatePostRequest request = new UpdatePostRequest( "Updated Content");
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> postService.updatePost(postId, request));
    }

    @Test
    void deletePost_deletes_post_when_exists() throws NotFoundException {
        // given
        Long postId = 1L;
        PostEntity existingEntity = new PostEntity();
        existingEntity.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingEntity));

        // when
        postService.deletePost(postId);

        // then
        Mockito.verify(postRepository).deleteById(postId);
    }

    @Test
    void deletePost_throws_NotFoundException_when_post_not_found() {
        // given
        Long postId = 999L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> postService.deletePost(postId));
    }
}