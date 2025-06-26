package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.persistence.entity.blogEntity.CommentEn;
import com.pondit.portfolio.persistence.entity.blogEntity.PostEn;
import com.pondit.portfolio.persistence.repository.blogRepo.CommentRepository;
import com.pondit.portfolio.persistence.repository.blogRepo.PostEnRepository;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostEnRepository postRepository;
    private final CommentRepository commentRepository;
    private final Parser markdownParser;
    private final HtmlRenderer htmlRenderer;


    public BlogController(PostEnRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.markdownParser = Parser.builder().build();
        this.htmlRenderer = HtmlRenderer.builder().build();
    }
    @GetMapping("{id}")
    public String detailPage(@PathVariable Long id, Model model) {
        System.out.println("Looking for post with ID: " + id);

        Optional<PostEn> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            System.out.println("Post NOT found, redirecting...");
            return "redirect:/";
        }

        PostEn post = postOptional.get();
        System.out.println("Post FOUND: " + post.getTitle());

        List<CommentEn> commentEns = commentRepository.findByPostId(id);
        Node document = markdownParser.parse(post.getContent());
        String markdownContent = htmlRenderer.render(document);

        model.addAttribute("post", post);
        model.addAttribute("comments", commentEns);
        model.addAttribute("markdownContent", markdownContent);

        return "blog/detail";
    }
    // For dynamic "Read More"
    @GetMapping("/api/{id}")
    public Map<String, String> getPostContent(@PathVariable Long id) {
        PostEn post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Node document = markdownParser.parse(post.getContent());
        String htmlContent = htmlRenderer.render(document);

        Map<String, String> response = new HashMap<>();
        response.put("title", post.getTitle());
        response.put("author", post.getAuthor());
        response.put("content", htmlContent);

        return response;
    }



    // Post class
    class Post {
        private Long id;
        private String title;
        private String content;
        private String author;
        private Date createdAt;
        private String createdAtFormatted;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAtFormatted() {
            return createdAtFormatted;
        }

        public void setCreatedAtFormatted(String createdAtFormatted) {
            this.createdAtFormatted = createdAtFormatted;
        }
    }

    // Comment class
    class Comment {
        private Long id;
        private Long postId;
        private String author;
        private String content;
        private Date createdAt;
        private String createdAtFormatted;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAtFormatted() {
            return createdAtFormatted;
        }

        public void setCreatedAtFormatted(String createdAtFormatted) {
            this.createdAtFormatted = createdAtFormatted;
        }
    }

}