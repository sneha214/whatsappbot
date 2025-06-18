package com.example.whatsaApp.Service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppMessageSender {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String accessToken;

    public void sendTextMessage(String recipientPhone, String messageText) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("to", recipientPhone);
        message.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", messageText);
        message.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        System.out.println("📨 Sent to WhatsApp: " + response.getBody());
    }
}

