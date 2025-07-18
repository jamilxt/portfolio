package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping({"/", "/blog"})
@RequiredArgsConstructor
@Controller
public class BlogController {
    private final PostService postService;
    private final ResumeConfig resumeConfig;

    @GetMapping
    public String indexPage(Model model, @RequestParam(defaultValue = "0",required = false) Integer page) {
        Page<Post> posts = postService.getAllPublishedPosts(page,5);
        model.addAttribute("postList", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        model.addAttribute("totalPages", posts.getTotalPages());
        return "blog/index";
    }

    @GetMapping("/details/{slug}")
    public String detailPage(@PathVariable String slug, Model model) throws NotFoundException {
        Post post = postService.getBySlug(slug);
        model.addAttribute("post", post);
        return "blog/detail";
    }
}
