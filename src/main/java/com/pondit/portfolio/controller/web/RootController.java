package com.pondit.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping
    public String indexPage(Model model) {
        return "blog/index";
    }
}
