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
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void getAll_returns_post_list() {
        // given
        Pageable pageable = Pageable.unpaged();
        PostEntity entity1 = new PostEntity();
        entity1.setId(1L);
        PostEntity entity2 = new PostEntity();
        entity2.setId(2L);
        List<PostEntity> entities = List.of(entity1, entity2);

        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(2L);

        when(postRepository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(postMapper.entityToDomain(entity1)).thenReturn(post1);
        when(postMapper.entityToDomain(entity2)).thenReturn(post2);

        // when
        List<Post> result = postService.getAllPosts(pageable);

        // then
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }@Test
    void create_returns_saved_post_id() {
        // given
        CreatePostRequest request = new CreatePostRequest("Sample Title", "Sample Content", "Sample Author");
        PostEntity entityToSave = new PostEntity();
        entityToSave.setTitle("new title");
        entityToSave.setContent("new content");
        entityToSave.setSlug("new-slug");
        PostEntity savedEntity = new PostEntity();
        savedEntity.setId(1L);

        when(postMapper.createRequestToEntity(request)).thenReturn(entityToSave);
        when(postRepository.save(entityToSave)).thenReturn(savedEntity);

        // when
        Long result = postService.create(request);

        // then
        assertEquals(1L, result);
    }
    @Test
    void getById_returns_post() throws NotFoundException {
        // given
        Long id = 1L;
        PostEntity entity = new PostEntity();
        entity.setId(id);
        entity.setTitle("Sample Title");
        entity.setContent("Sample Content");
        entity.setSlug("Sample Slug");
        Post post = new Post();
        post.setId(id);

        when(postRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(postMapper.entityToDomain(entity)).thenReturn(post);

        // when
        Post result = postService.getById(id);

        // then
        assertEquals(id, result.getId());
    }

    @Test
    void update_returns_success_message() throws NotFoundException {
        // given
        Long id = 1L;
        UpdatePostRequest request = new UpdatePostRequest("new content");
        PostEntity existingEntity = new PostEntity();
        existingEntity.setId(id);
        PostEntity updatedEntity = new PostEntity();
        updatedEntity.setId(id);

        when(postRepository.findById(id)).thenReturn(java.util.Optional.of(existingEntity));
        when(postMapper.updateRequestToEntity(request, existingEntity)).thenReturn(updatedEntity);
        when(postRepository.save(updatedEntity)).thenReturn(updatedEntity);

        // when
        String result = postService.update(id, request);

        // then
        assertEquals("Post updated successfully with ID: " + id, result);
    }

    @Test
    public void delete_returns_success_message() throws NotFoundException {
        // given
        Long id = 1L;
        PostEntity entity = new PostEntity();
        entity.setId(id);

        when(postRepository.findById(id)).thenReturn(java.util.Optional.of(entity));

        // when
        String result = postService.delete(id);

        // then
        assertEquals("Post deleted successfully with ID: " + id, result);
    }

}
