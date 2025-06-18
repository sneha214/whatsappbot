package com.example.whatsaApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private WhatsAppMessageSender messageSender;

    @Autowired
    private FirestoreService firestoreService;

    public void handleIncomingMessage(Map<String, Object> payload) {
        try {
            List<Map<String, Object>> entryList = (List<Map<String, Object>>) payload.get("entry");
            Map<String, Object> entry = entryList.get(0);

            List<Map<String, Object>> changesList = (List<Map<String, Object>>) entry.get("changes");
            Map<String, Object> change = changesList.get(0);

            Map<String, Object> value = (Map<String, Object>) change.get("value");

            if (!value.containsKey("messages")) {
                System.out.println("No message payload found.");
                return;
            }

            List<Map<String, Object>> messages = (List<Map<String, Object>>) value.get("messages");
            Map<String, Object> message = messages.get(0);

            String from = (String) message.get("from");
            Map<String, Object> text = (Map<String, Object>) message.get("text");
            String body = (String) text.get("body");

            System.out.println("📩 Message from " + from + ": " + body);

            messageSender.sendTextMessage(from, "👋 You said: " + body);

            firestoreService.saveMessage(from, body);
            System.out.println("✅ Saved to Firestore");

        } catch (Exception e) {
            System.out.println("⚠️ Error parsing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
