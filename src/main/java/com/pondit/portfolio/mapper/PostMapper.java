package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreatePostRequest;
import com.pondit.portfolio.model.dto.UpdatePostRequest;
import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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


    /**
     * Converts a CreatePostRequest DTO into a new PostEntity.
     * Used when saving a new post to the database.
     *
     * @param request the create request DTO from the client
     * @return a new PostEntity ready to be persisted
     */
    public PostEntity createRequestToEntity(CreatePostRequest request) {
        PostEntity entity = new PostEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }


    /**
     * Updates an existing PostEntity using data from UpdatePostRequest.
     * This modifies the original entity object for later saving.
     *
     * @param entity  the existing PostEntity to be updated
     * @param request the update request DTO containing new data
     * @return the updated PostEntity (for chaining or saving)
     */
    public PostEntity updatePostEntity(PostEntity entity, UpdatePostRequest request) {

        // 💡 Manual property mapping – more control than BeanUtils
        entity.setTitle(request.title());
        entity.setContent(request.content());
        entity.setSlug(request.slug());
        entity.setPublished(request.published());

        if (request.published()) {
            entity.setPublishedAt(request.publishedAt() != null ? request.publishedAt() : LocalDateTime.now());
        } else {
            entity.setPublishedAt(null);
        }
        return  entity;
    }

}
