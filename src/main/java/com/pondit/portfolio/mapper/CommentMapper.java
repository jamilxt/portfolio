package com.pondit.portfolio.mapper;

import com.pondit.portfolio.model.domain.Comment;
import com.pondit.portfolio.model.dto.CreateCommentRequest;
import com.pondit.portfolio.model.dto.UpdateCommentRequest;
import com.pondit.portfolio.persistence.entity.CommentEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentEntity createRequestToEntity(CreateCommentRequest request) {
        CommentEntity entity = new CommentEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public Comment entityToDomain(CommentEntity entity) {
        Comment domain = new Comment();
        BeanUtils.copyProperties(entity, domain);
        if (entity.getPost() != null) {
            domain.setPostId(entity.getPost().getId());
        }
        return domain;
    }
    public CommentEntity updateRequestToEntity(UpdateCommentRequest request,CommentEntity entity) {
        entity.setContent(request.content());
        return entity;
    }

}
