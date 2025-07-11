package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.persistence.entity.PostEntity;
import com.pondit.portfolio.persistence.repository.PostRepository;
import com.pondit.portfolio.service.PostService;
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

import java.util.List;

@RequestMapping({"/", "/blog"})
@RequiredArgsConstructor
@Controller
public class BlogController {
    private final PostService postService;
    private final ResumeConfig resumeConfig;

    @GetMapping
    public String indexPage(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "publishedAt");
        List<Post> posts = postService.getAllPublishedPosts(pageable);
        model.addAttribute("postList", posts);
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        return "blog/index";
    }

    @GetMapping("{slug}")
    public String detailPage(@PathVariable String slug) {
        return "blog/detail";
    }
}
