package com.pondit.portfolio.controller.web;

import com.pondit.portfolio.model.dto.ContactRequest;
import com.pondit.portfolio.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public String showContactForm(Model model) {
        if (!model.containsAttribute("contactRequest")) {
            model.addAttribute("contactRequest", new ContactRequest());
        }
        return "contact";
    }

    @PostMapping
    public String submitContactForm(
            @Valid @ModelAttribute("contactRequest") ContactRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactRequest", bindingResult);
            redirectAttributes.addFlashAttribute("contactRequest", request);
            return "redirect:/contact";
        }

        try {
            contactService.sendContactEmail(request);
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send your message. Please try again later.");
        }

        return "redirect:/contact";
    }
}
