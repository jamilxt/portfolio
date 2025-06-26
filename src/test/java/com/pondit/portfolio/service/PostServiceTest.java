package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    // Add this method to your PostServiceTest.java

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

    // Add this method to your PostServiceTest.java

    // Add this method to your PostServiceTest.java

    @Test
    void testCreate_setsPublishedAtWhenPublishedAndReturnsId() {
        CreatePostRequest request = new CreatePostRequest("title", "content", "slug", true);
        PostEntity entityToSave = new PostEntity();
        entityToSave.setPublished(true);

        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);

        when(postMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(entityToSave)).thenReturn(savedEntity);

        Long resultId = postService.create(request);

        assertEquals(1L, resultId);
        assertNotNull(entityToSave.getPublishedAt());
        verify(postRepository).save(entityToSave);
    }

    // Add this method to your PostServiceTest.java

    @Test
    void testGetById_returnsMappedPost() throws NotFoundException {
        Long postId = 1L;
        PostEntity postEntity = new PostEntity();
        Post post = new Post();

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postEntity));
        when(postMapper.entityToDomain(postEntity)).thenReturn(post);

        Post result = postService.getById(postId);

        assertEquals(post, result);
        verify(postRepository).findById(postId);
        verify(postMapper).entityToDomain(postEntity);
    }

    @Test
    void testUpdate_updatesPostEntity() throws NotFoundException {
        Long postId = 1L;
        UpdatePostRequest request = new UpdatePostRequest("title", "content", "slug", true);
        PostEntity existingEntity = new PostEntity();
        PostEntity updatedEntity = new PostEntity();

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(existingEntity));
        when(postMapper.updateRequestToEntity(request, existingEntity)).thenReturn(updatedEntity);

        postService.update(postId, request);

        verify(postRepository).save(updatedEntity);
    }

    @Test
    void testDelete_deletesPostById() throws NotFoundException {
        Long postId = 1L;
        PostEntity postEntity = new PostEntity();

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postEntity));

        postService.delete(postId);

        verify(postRepository).findById(postId);
        verify(postRepository).deleteById(postId);
    }

    // Add this method to your PostServiceTest.java

    @Test
    void testFindEntityById_returnsEntity_whenFound() throws Exception {
        Long postId = 1L;
        PostEntity postEntity = new PostEntity();

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postEntity));

        // Use reflection to access the private method
        java.lang.reflect.Method method = PostService.class.getDeclaredMethod("findEntityById", Long.class);
        method.setAccessible(true);
        PostEntity result = (PostEntity) method.invoke(postService, postId);

        assertEquals(postEntity, result);
        verify(postRepository).findById(postId);
    }

    @Test
    void testFindEntityById_throwsNotFoundException_whenNotFound() throws Exception {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        java.lang.reflect.Method method = PostService.class.getDeclaredMethod("findEntityById", Long.class);
        method.setAccessible(true);

        Exception exception = assertThrows(java.lang.reflect.InvocationTargetException.class, () -> {
            method.invoke(postService, postId);
        });

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(postRepository).findById(postId);
    }
}


