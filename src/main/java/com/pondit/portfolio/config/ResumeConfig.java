package com.pondit.portfolio.config;

import com.pondit.portfolio.model.domain.PersonalInfo;
import com.pondit.portfolio.model.domain.Education;
import com.pondit.portfolio.model.domain.Experience;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties(prefix = "resume")
@Getter
@Setter
public class ResumeConfig {
    private PersonalInfo personalInfo;
    private List<Education> education;
    private List<Experience> experience;
    private List<String> skills;
}
