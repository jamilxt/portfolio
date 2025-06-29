package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.model.domain.config.ResumeConfig;
import com.pondit.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/resume")
@Controller
public class ResumeController {
    private final ResumeConfig resumeConfig;
    private final ProjectService projectService;

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
        model.addAttribute("projects", projectService.getAll(Pageable.unpaged()));
        log.debug("Rendering index page");
        return "resume/index";
    }
}
