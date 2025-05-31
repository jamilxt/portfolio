package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import lombok.extern.slf4j.Slf4j;
import com.pondit.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RootController {
    @Autowired
    ResumeConfig resumeConfig;
    @Autowired
    ProjectService projectService;

    @Value("${site.title}")
    String siteTitle;

    @Value("${site.description}")
    String siteDescription;

    @GetMapping
    public String indexPage(Model model) {
        log.debug("Setting attributes for index page");
        model.addAttribute("siteTitle", siteTitle);
        model.addAttribute("siteDescription", siteDescription);
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        model.addAttribute("education", resumeConfig.getEducation());
        model.addAttribute("experience", resumeConfig.getExperience());
        model.addAttribute("skills", resumeConfig.getSkills());
        model.addAttribute("projects", projectService.getAllProjects());
        log.debug("Rendering index page");
        return "index";
    }

}
