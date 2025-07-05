package com.pondit.portfolio.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    private String content;
    @Setter
    private LocalDateTime createdAt;
}
