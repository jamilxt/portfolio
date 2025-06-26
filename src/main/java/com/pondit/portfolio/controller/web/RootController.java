package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.persistence.entity.blogEntity.PostEn;
import com.pondit.portfolio.persistence.repository.blogRepo.PostEnRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    private final PostEnRepository postRepository;

    public RootController(PostEnRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        Pageable topTwo = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PostEn> recentPosts = postRepository.findAll(topTwo);

        model.addAttribute("posts", recentPosts.getContent());
        return "blog/index"; // This points to templates/index.html
    }
}