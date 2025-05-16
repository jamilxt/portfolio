package com.pondit.portfolio.controller.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = switch (statusCode != null ? statusCode : 500) {
            case 403 -> "Access Denied: You don't have permission to view this page.";
            case 404 -> "Page Not Found: The page you're looking for doesn't exist or has been moved.";
            case 500 -> "Internal Server Error: Something went wrong on our end.";
            default -> "An unexpected error occurred.";
        };

        model.addAttribute("errorCode", statusCode != null ? statusCode : "Unknown");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}