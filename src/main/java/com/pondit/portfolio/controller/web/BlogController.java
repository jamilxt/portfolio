package com.pondit.portfolio.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pondit.portfolio.service.PostService;
import com.pondit.portfolio.model.domain.Post;

import java.util.List;


@RequestMapping("/blog")
@Controller
@AllArgsConstructor
public class BlogController {

    private final PostService postService;

    @GetMapping
    public String indexPage(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "publishedAt");
        List<Post> posts = postService.getAllPosts(pageable);
        model.addAttribute("postList", posts);
        return "blog/index";
    }

    @GetMapping("{slug}")
    public String detailPage(@PathVariable String slug) {
        return "blog/detail";
    }
}
