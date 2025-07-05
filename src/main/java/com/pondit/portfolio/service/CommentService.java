package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.CommentMapper;
import com.pondit.portfolio.model.domain.Comment;
import com.pondit.portfolio.model.dto.CreateCommentRequest;
import com.pondit.portfolio.model.dto.UpdateCommentRequest;
import com.pondit.portfolio.persistence.entity.CommentEntity;
import com.pondit.portfolio.persistence.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public Long createComment(CreateCommentRequest request) {
        var comment = commentMapper.createRequestToEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        var savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public List<Comment> getAllComments() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        return commentEntities.stream()
                .map(commentMapper::entityToDomain)
                .toList();
    }

    public void deleteComment(Long id)throws NotFoundException {
        this.findEntityById(id);
        commentRepository.deleteById(id);
    }

    public Comment getCommentById(Long id)throws  NotFoundException {
        var commentEntity = this.findEntityById(id);
        return commentMapper.entityToDomain(commentEntity);
    }

    public void updateComment(Long id, UpdateCommentRequest request)throws NotFoundException {
        var commentEntity = this.findEntityById(id);
        var updateRequestToEntity = commentMapper.updateRequestToEntity(request, commentEntity);
        commentRepository.save(updateRequestToEntity);
    }

    private CommentEntity findEntityById(Long id) throws NotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }



}
