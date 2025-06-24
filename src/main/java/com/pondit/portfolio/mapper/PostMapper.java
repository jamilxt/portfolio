package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post entityToDomain(PostEntity post){
        Post domain=new Post();
        BeanUtils.copyProperties(post,domain);
        return domain;
    }

    public PostEntity domainToEntity(CreatePostRequest post){
        PostEntity entity=new PostEntity();
        BeanUtils.copyProperties(post,entity);
        return entity;
    }

    public PostEntity updateRequestToEntity(UpdatePostRequest request, PostEntity entity) {
        entity.setContent(request.content());
        return entity;
    }
}