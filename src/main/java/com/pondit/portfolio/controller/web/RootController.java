package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.config.ResumeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @Autowired
    ResumeConfig resumeConfig;

    @Value("${site.title}")
    String siteTitle;

    @Value("${site.description}")
    String siteDescription;

    private static Logger logger = LoggerFactory.getLogger(RootController.class);

    @GetMapping
    public String indexPage(Model model) {
        logger.debug("Setting attributes for index page");
        model.addAttribute("siteTitle", siteTitle);
        model.addAttribute("siteDescription", siteDescription);
        model.addAttribute("personalInfo", resumeConfig.getPersonalInfo());
        model.addAttribute("education", resumeConfig.getEducation());
        model.addAttribute("experience", resumeConfig.getExperience());
        model.addAttribute("skills", resumeConfig.getSkills());
        logger.debug("Rendering index page");
        return "index";
    }

}
