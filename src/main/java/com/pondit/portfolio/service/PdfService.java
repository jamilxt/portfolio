package com.pondit.portfolio.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.pondit.portfolio.config.ResumeConfig;
import com.pondit.portfolio.model.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {
    private final ResumeConfig resumeConfig;
    private final ProjectService projectService;

    public void generateResumePdf(OutputStream outputStream) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph("Resume"));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Personal Information:"));
        if(resumeConfig.getPersonalInfo() != null) {
            document.add(new Paragraph("Name: " + resumeConfig.getPersonalInfo().getName()));
            document.add(new Paragraph("Email: " + resumeConfig.getPersonalInfo().getEmail()));
            document.add(new Paragraph("LinkedIn: " + resumeConfig.getPersonalInfo().getLinkedinUrl()));
            document.add(new Paragraph("GitHub: " + resumeConfig.getPersonalInfo().getGithubUrl()));
            document.add(new Paragraph(" "));
        }

        document.add(new Paragraph("Education:"));
        if(resumeConfig.getEducation() != null) {
            resumeConfig.getEducation().forEach(education -> {
                try {
                    document.add(new Paragraph("Degree: " + education.getDegree()));
                    document.add(new Paragraph("Institution: " + education.getInstitution()));
                    document.add(new Paragraph("Year: " + education.getYears()));
                } catch (Exception e) {
                    throw new RuntimeException("Error adding education to PDF", e);
                }
            });
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Experience:"));
        if(resumeConfig.getExperience() != null) {
            resumeConfig.getExperience().forEach(experience -> {
                try {
                    document.add(new Paragraph("Position: " + experience.getTitle()));
                    document.add(new Paragraph("Company: " + experience.getCompany()));
                    document.add(new Paragraph("Years: " + experience.getYears()));
                    document.add(new Paragraph("Description: " + experience.getDescription()));
                } catch (Exception e) {
                    throw new RuntimeException("Error adding experience to PDF", e);
                }
            });
        }
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Skills:"));
        if(resumeConfig.getSkills() != null) {
            resumeConfig.getSkills().forEach(skill -> {
                try {
                    document.add(new Paragraph(skill));
                } catch (Exception e) {
                    throw new RuntimeException("Error adding skills to PDF", e);
                }
            });
        }
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Projects:"));
        List<Project> projects = projectService.getAll(Pageable.unpaged());
        if(projects == null || projects.isEmpty()) {
            document.add(new Paragraph("No projects available."));
        } else {
            for (Project project : projects) {
                document.add(new Paragraph("Project Name: " + project.getName()));
                document.add(new Paragraph("Description: " + project.getDescription()));
                document.add(new Paragraph(" "));
            }
        }



        document.close();
    }
}