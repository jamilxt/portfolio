package com.pondit.portfolio.service;

import com.pondit.portfolio.model.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class ContactService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendContactEmail(ContactRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("dreamshadow.ab@gmail.com"); // Receiving email (your inbox)
            message.setFrom(fromEmail);                // Authenticated sender
            message.setSubject("Contact Form Submission from " + request.getName());
            message.setText(
                    "Name: " + request.getName() + "\n" +
                            "Email: " + request.getEmail() + "\n\n" +
                            "Message:\n" + request.getMessage()
            );

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send contact email", e);
        }
    }
}


