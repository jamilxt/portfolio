package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import com.pondit.portfolio.exception.custom.NotFoundException;
import com.pondit.portfolio.model.domain.Comment;
import com.pondit.portfolio.model.domain.Post;
import com.pondit.portfolio.model.dto.CreateCommentRequest;
import com.pondit.portfolio.service.CommentService;
import com.pondit.portfolio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping({"/", "/blog"})
@RequiredArgsConstructor
@Controller
public class BlogController {
    private final PostService postService;
    private final ResumeConfig resumeConfig;
    private final CommentService commentService;

    @GetMapping
    public String indexPage(Model model) throws NotFoundException {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "publishedAt");
        List<Post> posts = postService.getAllPublishedPosts(pageable);

        Map<Long,List<Comment>> commentMap = new HashMap<>();
        for(var post:posts)
        {
            List<Comment> comments = commentService.getCommentsByPostId(post.getId());
            commentMap.put(post.getId(), comments);
        }

        model.addAttribute("postList", posts);
        model.addAttribute("commentList", commentMap);
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        return "blog/index";
    }

    @PostMapping("/comment")
    public String addComment(@ModelAttribute CreateCommentRequest request) throws NotFoundException {
        commentService.createComment(request);
        return "redirect:/blog";
    }


    @GetMapping("{slug}")
    public String detailPage(@PathVariable String slug) {
        return "blog/detail";
    }
}
