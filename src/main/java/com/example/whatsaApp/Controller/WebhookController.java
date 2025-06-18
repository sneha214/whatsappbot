package com.example.whatsaApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private MessageService messageService;


    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") String mode,
                                                @RequestParam("hub.verify_token") String token,
                                                @RequestParam("hub.challenge") String challenge) {
        if ("subscribe".equals(mode) && "sneha_verify_token_123".equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(403).body("Verification failed");
    }


    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, Object> payload) {
        System.out.println("🔥 Webhook POST received:");
        System.out.println(payload); // <-- Add this
        messageService.handleIncomingMessage(payload);
        return ResponseEntity.ok("EVENT_RECEIVED");
    }


}
