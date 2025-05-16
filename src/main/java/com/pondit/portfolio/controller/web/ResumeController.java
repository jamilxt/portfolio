package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import com.pondit.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/resume")
@Controller
public class ResumeController {
    @Autowired
    ResumeConfig resumeConfig;
    @Autowired
    ProjectService projectService;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        model.addAttribute("education", resumeConfig.getEducation());
        model.addAttribute("experience", resumeConfig.getExperience());
        model.addAttribute("skills", resumeConfig.getSkills());
        model.addAttribute("projects", projectService.getAllProjects());
        return "resume/index";
    }

}
