package com.pondit.portfolio.persistence.repository;

import com.pondit.portfolio.persistence.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByPublishedIsTrue(Pageable pageable);

    boolean existsBySlug(String slug);

    List<PostEntity> findTopByPublishedIsTrueOrderByPublishedAtDesc(Pageable pageable);
}
