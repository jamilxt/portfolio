package com.pondit.portfolio.controller.web.admin;

import com.pondit.portfolio.model.dto.NotificationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping
    public String adminDashboard(Model model) {

        List<NotificationRequest> notifications = List.of(
                new NotificationRequest("July 29, 2025", "Hello there",  "/reports/1"),
                new NotificationRequest("July 28, 2025", "How are things", "/transactions"),
                new NotificationRequest("July 27, 2025", "Unusually high spending detected.", "/alerts"),
                new NotificationRequest("July 29, 2025", "⚠️ THIS IS A TEST MESSAGE", "/debug")
        );
        model.addAttribute("notifications", notifications);
        return "admin/index";
    }
}
