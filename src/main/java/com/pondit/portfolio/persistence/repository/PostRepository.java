package com.pondit.portfolio.persistence.repository;

import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
