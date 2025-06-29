package com.pondit.portfolio.model.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "resume")
@Getter
@Setter
public class ResumeConfig {
    private PersonalInfo personalInfo;
    private List<Education> education;
    private List<Experience> experience;
    private List<String> skills;

    @Getter
    @Setter
    public static class PersonalInfo {
        private String name;
        private String email;
        private String linkedinUrl;
        private String githubUrl;
    }

    @Getter
    @Setter
    public static class Education {
        private String degree;
        private String institution;
        private String years;
        private String description;
    }

    @Getter
    @Setter
    public static class Experience {
        private String title;
        private String company;
        private String years;
        private String description;
    }
}
