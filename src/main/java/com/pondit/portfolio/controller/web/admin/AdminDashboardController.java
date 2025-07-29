package com.pondit.portfolio.controller.web.admin;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final PostService postService;
    // private final ResumeConfig resumeConfig;

    @GetMapping
    public String adminDashboard(Model model, @ParameterObject Pageable pageable) {
        List<Post> postsPublished = postService.getAllPostsPublished(pageable);
        List<Post> postsPending = postService.getAllPostsPending(pageable);
        model.addAttribute("publishedPosts", postsPublished.size());
        model.addAttribute("pendingPosts", postsPending.size());
        return "admin/index";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/all-posts")
    public String showAllPosts(Model model, @ParameterObject Pageable pageable) {
        List<Post> posts = postService.getAllPosts(pageable);
        model.addAttribute("postList", posts);
        return "admin/post_list";
    }
}
