package com.pondit.portfolio.persistence.repository.blogRepo;

import com.pondit.portfolio.persistence.entity.blogEntity.PostEn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEnRepository extends JpaRepository<PostEn, Long> {
}
