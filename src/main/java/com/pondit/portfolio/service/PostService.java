package com.pondit.portfolio.service;
import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.PostMapper;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public List<Post> getAllPost(Pageable pageable){
        List<PostEntity>getPostData = postRepository.findAll(pageable).getContent();
        return getPostData.stream().map(entity -> {
            return postMapper.entityToDomain(entity);
        }).toList();
    }

    public void createPost(CreatePostRequest createPostRequest){
        var postEntity = postMapper.domainToEntity(createPostRequest);
        postRepository.save(postEntity);
    }

    public void updatePost(Long id, UpdatePostRequest updatePostRequest) throws NotFoundException {
        var postEntity = this.findEntityById(id);
        postMapper.updateRequestToEntity(updatePostRequest, postEntity);
        postRepository.save(postEntity);
    }

    public void deletePost(Long id) throws NotFoundException{
        this.findEntityById(id);
        postRepository.deleteById(id);
    }

    private PostEntity findEntityById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

}
