package com.pondit.portfolio.service;

import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getAllPosts(Pageable pageable) {
        List<PostEntity> entityList = postRepository.findAll(pageable).getContent();
        return entityList.stream().map(postMapper::entityToDomain).toList();
    }
}
