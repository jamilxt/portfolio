package com.example.springAs1.service.impl;

import com.example.springAs1.exception.custom.NotFoundException;
import com.example.springAs1.mapper.PostMapper;
import com.example.springAs1.model.domain.Post;
import com.example.springAs1.model.dto.CreatePostRequest;
import com.example.springAs1.model.dto.UpdatePostRequest;
import com.example.springAs1.persistence.entity.PostEntity;
import com.example.springAs1.persistence.repository.PostRepository;
import com.example.springAs1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository repository;
    @Autowired
    private PostMapper mapper;

    @Override
    public ResponseEntity<Post> create(CreatePostRequest request) {
        PostEntity entity =  mapper.createPostRequestToEntity(request);
        PostEntity saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDomain(saved));
    }

    @Override
    public ResponseEntity<List<Post>> getAll(Pageable pageable) {
        List<PostEntity> entities = repository.findAll(pageable).getContent();
        return ResponseEntity.status(HttpStatus.OK)
                .body(entities.stream()
                        .map(mapper::toDomain).toList());
    }

    @Override
    public ResponseEntity<Post> get(Long id) throws NotFoundException {
        PostEntity entity = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found for get"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDomain(entity));
    }

    @Override
    public ResponseEntity<Post> update(Long id, UpdatePostRequest request) throws NotFoundException{
        PostEntity entity = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found for update"));
        PostEntity updated = mapper.updatePostRequestToEntity(request, entity);
       // updated.setId(id);
        PostEntity saved = repository.save(updated);
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDomain(saved));
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws NotFoundException{
        PostEntity entity = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found for delete"));
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Delete post done!");
    }
}
