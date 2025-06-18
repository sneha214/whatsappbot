package com.example.whatsaApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "✅ WhatsApp Spring Boot Chatbot is running!";
    }
}
