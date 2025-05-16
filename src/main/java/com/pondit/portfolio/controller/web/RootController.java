package com.pondit.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RootController {
    static class PersonalInfo {
        private String name;
        private String email;
        private String linkedinUrl;
        private String githubUrl;

        public PersonalInfo(String name, String email, String linkedinUrl, String githubUrl) {
            this.name = name;
            this.email = email;
            this.linkedinUrl = linkedinUrl;
            this.githubUrl = githubUrl;
        }

        // Getters
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getLinkedinUrl() { return linkedinUrl; }
        public String getGithubUrl() { return githubUrl; }
    }

    static class Education {
        private String degree;
        private String institution;
        private String years;
        private String description;

        public Education(String degree, String institution, String years, String description) {
            this.degree = degree;
            this.institution = institution;
            this.years = years;
            this.description = description;
        }

        // Getters
        public String getDegree() { return degree; }
        public String getInstitution() { return institution; }
        public String getYears() { return years; }
        public String getDescription() { return description; }
    }

    static class Experience {
        private String title;
        private String company;
        private String years;
        private String description;

        public Experience(String title, String company, String years, String description) {
            this.title = title;
            this.company = company;
            this.years = years;
            this.description = description;
        }

        // Getters
        public String getTitle() { return title; }
        public String getCompany() { return company; }
        public String getYears() { return years; }
        public String getDescription() { return description; }
    }

    @GetMapping
    public String indexPage(Model model) {
        // Personal Info
        PersonalInfo personalInfo = new PersonalInfo(
                "John Doe",
                "john.doe@example.com",
                "https://linkedin.com/in/johndoe",
                "https://github.com/johndoe"
        );
        model.addAttribute("personalInfo", personalInfo);

        // Education
        List<Education> education = Arrays.asList(
                new Education(
                        "B.S. in Computer Science",
                        "University of Example",
                        "2018 - 2022",
                        "Graduated with honors, focusing on software engineering and distributed systems."
                )
        );
        model.addAttribute("education", education);

        // Experience
        List<Experience> experience = Arrays.asList(
                new Experience(
                        "Software Engineer",
                        "Tech Corp",
                        "2022 - Present",
                        "Developed scalable backend systems using Spring Boot and microservices architecture."
                ),
                new Experience(
                        "Junior Developer",
                        "Startup Inc.",
                        "2021 - 2022",
                        "Built responsive web applications with Java and Thymeleaf, improving user engagement by 20%."
                )
        );
        model.addAttribute("experience", experience);

        // Skills
        List<String> skills = Arrays.asList("Java", "Spring Boot", "Microservices", "PostgreSQL", "Thymeleaf", "Tailwind CSS");
        model.addAttribute("skills", skills);
        return "index";
    }

}
