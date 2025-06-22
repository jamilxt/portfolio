package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post entityToDomain(PostEntity postEntity){
        Post domain = new Post();
        BeanUtils.copyProperties(postEntity, domain);
        return domain;
    }
}
