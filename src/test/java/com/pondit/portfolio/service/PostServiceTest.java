package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        PostEntity entity = new PostEntity();
        Post post = new Post();
        when(postRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(postMapper.entityToDomain(entity)).thenReturn(post);

        List<Post> result = postService.getAll(PageRequest.of(0, 10));
        assertEquals(1, result.size());
    }

    @Test
    void testCreate() {
        CreatePostRequest request = new CreatePostRequest();
        PostEntity entityToSave = new PostEntity();
        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);

        when(postMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(entityToSave)).thenReturn(savedEntity);

        Long result = postService.create(request);
        assertEquals(1L, result);
    }

    @Test
    void testGetByIdSuccess() {
        PostEntity entity = new PostEntity();
        Post post = new Post();
        when(postRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(postMapper.entityToDomain(entity)).thenReturn(post);

        Post result = postService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetByIdNotFound() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> postService.getById(99L));
    }

    @Test
    void testUpdateSuccess() {
        UpdatePostRequest request = new UpdatePostRequest();
        PostEntity entity = new PostEntity();
        PostEntity updatedEntity = new PostEntity();

        when(postRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(postMapper.updateRequestToEntity(request, entity)).thenReturn(updatedEntity);

        postService.update(1L, request);
        verify(postRepository).save(updatedEntity);
    }

    @Test
    void testUpdateNotFound() {
        UpdatePostRequest request = new UpdatePostRequest();
        when(postRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> postService.update(999L, request));
    }

    @Test
    void testDeleteSuccess() {
        PostEntity entity = new PostEntity();
        when(postRepository.findById(1L)).thenReturn(Optional.of(entity));
        postService.delete(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> postService.delete(99L));
    }
}
