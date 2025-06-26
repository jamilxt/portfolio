package com.pondit.portfolio.persistence.repository.blogRepo;

import com.pondit.portfolio.persistence.entity.blogEntity.CommentEn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEn, Long> {
    List<CommentEn> findByPostId(Long postId);
}
