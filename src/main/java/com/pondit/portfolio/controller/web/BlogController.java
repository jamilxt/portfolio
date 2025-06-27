package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/", "/blog"})
@RequiredArgsConstructor
@Controller
public class BlogController {
    private final PostRepository postRepository;

    @GetMapping
    public String indexPage(Model model) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "publishedAt"));
        Page<PostEntity> posts = postRepository.findAll(pageable);
        model.addAttribute("controllerPosts", posts.getContent());
        return "blog/index";
    }

    @GetMapping("{slug}")
    public String detailPage(@PathVariable String slug) {
        return "blog/detail";
    }
}
