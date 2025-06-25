package com.example.springAs1.service;

import com.example.springAs1.exception.custom.NotFoundException;
import com.example.springAs1.mapper.PostMapper;
import com.example.springAs1.model.domain.Post;
import com.example.springAs1.model.dto.CreatePostRequest;
import com.example.springAs1.model.dto.UpdatePostRequest;
import com.example.springAs1.persistence.entity.PostEntity;
import com.example.springAs1.persistence.repository.PostRepository;
import com.example.springAs1.service.impl.PostServiceImpl;
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
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostServiceImpl service;

    @Mock
    private PostRepository repository;

    @Mock
    private PostMapper mapper;

    @Test
    void createPost_published_true_expect_publishedAt_Date_Now_test(){
        // dif request
        CreatePostRequest request = new CreatePostRequest("New Test post", "New Test post content", "New Test post slug", true);
        PostEntity entityToSaved = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();

        PostEntity savedEntity = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(true)
                .id(1L)
                .publishedAt(LocalDateTime.now())
                .build();
        Post domainPost = Post.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(true)
                .id(1L)
                .publishedAt(LocalDateTime.now())
                .build();

        when (mapper.createPostRequestToEntity(request)).thenReturn(entityToSaved);
        when(repository.save(any(PostEntity.class))).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(domainPost);

        Long createId = Objects.requireNonNull(service.create(request).getBody()).getId();

        Assertions.assertEquals(1L, createId);

    }

    @Test
    void createPost_published_false_expect_publishedAt_Date_Null_test(){
        // dif request
        CreatePostRequest request = new CreatePostRequest("New Test post", "New Test post content", "New Test post slug", false);
        PostEntity entityToSaved = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(false)
                .publishedAt(null)
                .build();

        PostEntity savedEntity = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(false)
                .id(1L)
                .publishedAt(null)
                .build();
        Post domainPost = Post.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(false)
                .id(1L)
                .publishedAt(null)
                .build();

        when (mapper.createPostRequestToEntity(request)).thenReturn(entityToSaved);
        when(repository.save(any(PostEntity.class))).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(domainPost);

        Long createId = Objects.requireNonNull(service.create(request).getBody()).getId();

        Assertions.assertEquals(1L, createId);

    }

    @Test
    void createPost_published_Null_expect_publishedAt_Date_Null_test(){
        // dif request
        CreatePostRequest request = new CreatePostRequest("New Test post", "New Test post content", "New Test post slug", null);
        PostEntity entityToSaved = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(null)
                .publishedAt(null)
                .build();

        PostEntity savedEntity = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(null)
                .id(1L)
                .publishedAt(null)
                .build();
        Post domainPost = Post.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(null)
                .id(1L)
                .publishedAt(null)
                .build();

        when (mapper.createPostRequestToEntity(request)).thenReturn(entityToSaved);
        when(repository.save(any(PostEntity.class))).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(domainPost);

        Long createId = Objects.requireNonNull(service.create(request).getBody()).getId();

        Assertions.assertEquals(1L, createId);

    }

    @Test
    void getPost_given_valid_id_test(){
        // dif request
        Long requestId = 1L;
        PostEntity entityReturnWhenFindById = PostEntity.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .id(requestId)
                .build();


        Post domainPost = Post.builder()
                .title("New Test post")
                .content("New Test post content")
                .slug("New Test post slug")
                .published(true)
                .id(requestId)
                .publishedAt(LocalDateTime.now())
                .build();

        when(repository.findById(requestId)).thenReturn(Optional.of(entityReturnWhenFindById));
        when(mapper.toDomain(entityReturnWhenFindById)).thenReturn(domainPost);

        Post response = Objects.requireNonNull(service.get(requestId).getBody());

        Assertions.assertEquals(requestId, response.getId());
        Assertions.assertEquals(domainPost, response);
    }

    @Test
    void getPost_given_invalid_id_throw_Not_found_exception_test(){
        // dif request
        Long requestId = 999L;
        when(repository.findById(requestId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()->service.get(requestId));
    }
    @Test
    void getAllPost_given_post_list_test(){
        Pageable pageable = Pageable.unpaged();
        PostEntity post1 = PostEntity.builder()
                .id(1L)
                .title("Test title")
                .content("Test content")
                .slug("Test slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        PostEntity post2 = PostEntity.builder()
                .id(2L)
                .title("Test title")
                .content("Test content")
                .slug("Test slug")
                .published(false)
                .publishedAt(null)
                .build();

        List<PostEntity> entities = List.of(post1, post2);
        Post mock1 = Post.builder()
                .id(1L)
                .title("Test title")
                .content("Test content")
                .slug("Test slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        Post mock2 = Post.builder()
                .id(2L)
                .title("Test title")
                .content("Test content")
                .slug("Test slug")
                .published(false)
                .publishedAt(null)
                .build();

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(entities));
        when(mapper.toDomain(post1)).thenReturn(mock1);
        when(mapper.toDomain(post2)).thenReturn(mock2);

        List<Post> response = Objects.requireNonNull(service.getAll(pageable).getBody());

        Assertions.assertEquals(1L, response.get(0).getId());
        Assertions.assertEquals(2L, response.get(1).getId());

    }

    @Test
    void updatePost_updates_all_when_exists_test(){

        Long updateId = 1L;
        // publishedAt changes depend on published
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(true)
                .build();

        PostEntity oldPost = PostEntity.builder()
                .id(1L)
                .title("Test old title")
                .content("Test old content")
                .slug("Test old slug")
                .published(false)
                .publishedAt(null)
                .build();
        PostEntity newPost = PostEntity.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        Post domainPost = Post.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        when(repository.findById(updateId)).thenReturn(Optional.of(oldPost));
        when(mapper.updatePostRequestToEntity(request, oldPost)).thenReturn(newPost);
        when(repository.save(newPost)).thenReturn(newPost);
        when(mapper.toDomain(newPost)).thenReturn(domainPost);

        Post response = Objects.requireNonNull(service.update(updateId, request).getBody());
        System.out.println("=============== response after update: "+ response);
        Assertions.assertEquals(domainPost.getTitle(), response.getTitle());
        Assertions.assertEquals(domainPost.getSlug(), response.getSlug());
        Assertions.assertEquals(domainPost.getContent(), response.getContent());
        Assertions.assertEquals(domainPost.getPublished(), response.getPublished());
        Assertions.assertEquals(domainPost.getPublishedAt(), response.getPublishedAt());
        Mockito.verify(repository).save(newPost);
    }

    @Test
    void updatePost_updates_publishedAt_null_when_published_false_test(){

        Long updateId = 1L;
        // publishedAt changes depend on published
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(false)
                .build();

        PostEntity oldPost = PostEntity.builder()
                .id(1L)
                .title("Test old title")
                .content("Test old content")
                .slug("Test old slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        PostEntity newPost = PostEntity.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(false)
                .publishedAt(null)
                .build();
        Post domainPost = Post.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(false)
                .publishedAt(null)
                .build();
        when(repository.findById(updateId)).thenReturn(Optional.of(oldPost));
        when(mapper.updatePostRequestToEntity(request, oldPost)).thenReturn(newPost);
        when(repository.save(newPost)).thenReturn(newPost);
        when(mapper.toDomain(newPost)).thenReturn(domainPost);

        Post response = Objects.requireNonNull(service.update(updateId, request).getBody());
        System.out.println("=============== response after update: "+ response);
        Assertions.assertEquals(domainPost.getTitle(), response.getTitle());
        Assertions.assertEquals(domainPost.getSlug(), response.getSlug());
        Assertions.assertEquals(domainPost.getContent(), response.getContent());
        Assertions.assertEquals(domainPost.getPublished(), response.getPublished());
        Assertions.assertEquals(domainPost.getPublishedAt(), response.getPublishedAt());
        Mockito.verify(repository).save(newPost);
    }

    @Test
    void updatePost_updates_published_false_default_publishedAt_null_when_published_null_test(){

        Long updateId = 1L;
        // publishedAt changes depend on published
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(null)
                .build();

        PostEntity oldPost = PostEntity.builder()
                .id(1L)
                .title("Test old title")
                .content("Test old content")
                .slug("Test old slug")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();
        PostEntity newPost = PostEntity.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(false)
                .publishedAt(null)
                .build();
        Post domainPost = Post.builder()
                .id(1L)
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(false)
                .publishedAt(null)
                .build();
        when(repository.findById(updateId)).thenReturn(Optional.of(oldPost));
        when(mapper.updatePostRequestToEntity(request, oldPost)).thenReturn(newPost);
        when(repository.save(newPost)).thenReturn(newPost);
        when(mapper.toDomain(newPost)).thenReturn(domainPost);

        Post response = Objects.requireNonNull(service.update(updateId, request).getBody());
        System.out.println("=============== response after update: "+ response);
        Assertions.assertEquals(domainPost.getTitle(), response.getTitle());
        Assertions.assertEquals(domainPost.getSlug(), response.getSlug());
        Assertions.assertEquals(domainPost.getContent(), response.getContent());
        Assertions.assertEquals(domainPost.getPublished(), response.getPublished());
        Assertions.assertEquals(domainPost.getPublishedAt(), response.getPublishedAt());
        Mockito.verify(repository).save(newPost);
    }

    @Test
    void updatePost_throw_Not_found_invalid_id_test(){

        Long requestId = 999L;
        // publishedAt changes depend on published
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Test new title")
                .content("Test new content")
                .slug("Test new slug")
                .published(null)
                .build();
        when(repository.findById(requestId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, ()->service.update(requestId, request));
    }

    @Test
    void deletePost_valid_id_test(){

        Long requestId = 1L;
        PostEntity oldPost = PostEntity.builder()
                .content("delete post content")
                .title("delete post title")
                .slug("delete post slug")
                .publishedAt(LocalDateTime.now())
                .published(true)
                .build();
        when(repository.findById(requestId)).thenReturn(Optional.of(oldPost));

        service.delete(requestId);

        Mockito.verify(repository).deleteById(requestId);
    }

    @Test
    void deletePost_throw_not_found_invalid_id_test(){

        Long requestId = 999L;

        when(repository.findById(requestId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, ()->service.delete(requestId));
    }


}
