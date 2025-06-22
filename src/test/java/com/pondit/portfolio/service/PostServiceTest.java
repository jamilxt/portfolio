package com.pondit.portfolio.service;

import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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

}
