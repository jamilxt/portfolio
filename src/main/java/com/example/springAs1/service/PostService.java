package com.example.springAs1.service;


import com.example.springAs1.model.domain.Post;
import com.example.springAs1.model.dto.CreatePostRequest;
import com.example.springAs1.model.dto.UpdatePostRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    ResponseEntity<Post> create(CreatePostRequest request);
    ResponseEntity<List<Post>> getAll(Pageable pageable);
    ResponseEntity<Post> get(Long id);
    ResponseEntity<?> update(Long id, UpdatePostRequest request);
    ResponseEntity<?> delete(Long id);
}
