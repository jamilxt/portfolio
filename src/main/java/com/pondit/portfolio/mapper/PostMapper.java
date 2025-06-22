package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    /**
     * Maps PostEntity (persistence layer) to Post (domain layer)
     *
     * @param entity - the PostEntity fetched from the database
     * @return Post - the domain object used in business logic or API
     */
    public Post entryToDomain(PostEntity entity) {
        Post domain = new Post();

        /*
        Copies matching fields from entity → domain using reflection
             Note: BeanUtils relies on runtime reflection, which:
             - Dynamically inspects getters and setters
             - Is slower than manual mapping
             - Is harder to debug if field names/types mismatch
             - Should be replaced with manual mapping or MapStruct for large-scale apps
         */
        BeanUtils.copyProperties(entity, domain);
        return domain;
    }
}
