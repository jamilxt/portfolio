package com.pondit.portfolio.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    @GetMapping
    public String adminDashboard() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/register")
    public String adminRegister() {
        return "admin/register";
    }

    @GetMapping("/forget-password")
    public String adminForgetPassword() {
        return "admin/forget-password";
    }

    @GetMapping("/all-posts")
    public String showAllPosts() {
        return "admin/post_list";
    }
}
