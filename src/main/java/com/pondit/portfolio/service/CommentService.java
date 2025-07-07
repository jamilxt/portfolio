package com.pondit.portfolio.service;

import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.mapper.CommentMapper;
import com.pondit.portfolio.model.domain.Comment;
import com.pondit.portfolio.model.dto.CreateCommentRequest;
import com.pondit.portfolio.model.dto.UpdateCommentRequest;
import com.pondit.portfolio.persistence.entity.CommentEntity;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.CommentRepository;
import com.pondit.portfolio.persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;


    public Long createComment(CreateCommentRequest request) throws NotFoundException {
        var comment = commentMapper.createRequestToEntity(request);
        PostEntity post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + request.postId()));
        comment.setPost(post);
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

    public List<Comment> getCommentsByPostId(Long postId)throws NotFoundException {
        List<CommentEntity> commentEntities = commentRepository.findByPostId(postId);
        return commentEntities.stream()
                .map(commentMapper::entityToDomain)
                .toList();
    }

    private CommentEntity findEntityById(Long id) throws NotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }



}
