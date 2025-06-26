package com.pondit.portfolio.persistence.entity.blogEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Entity
@Setter
@Table(name = "cmnt")
public class CommentEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId; // Or use @ManyToOne if you want a relationship
    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Date createdAt;
}