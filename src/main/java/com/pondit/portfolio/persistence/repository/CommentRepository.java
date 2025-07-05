package com.pondit.portfolio.persistence.repository;

import com.pondit.portfolio.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
