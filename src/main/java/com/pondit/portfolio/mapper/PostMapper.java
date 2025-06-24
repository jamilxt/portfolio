package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.model.dto.UpdateProjectRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.entity.ProjectEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post entityToDomain(PostEntity entity){
        Post domain = new Post();
        BeanUtils.copyProperties(entity, domain);
        return domain;
    }

    public PostEntity createRequestToEntity(CreatePostRequest request){
        PostEntity entity = new PostEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public PostEntity updateRequestToEntity(UpdatePostRequest request, PostEntity entity){

        entity.setTitle(request.title());
        entity.setContent(request.content());
        entity.setSlug(request.slug());
        entity.setPublished(request.published());

        return entity;
    }
}
