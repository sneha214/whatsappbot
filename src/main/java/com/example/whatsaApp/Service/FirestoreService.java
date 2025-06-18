package com.example.whatsaApp.Service;

import com.google.cloud.firestore.Firestore;

import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Map;


@Service
public class FirestoreService {

    public void saveMessage(String sender, String message) {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> data = new HashMap<>();
        data.put("sender", sender);
        data.put("message", message);
        data.put("timestamp", System.currentTimeMillis());

        db.collection("chat_logs").add(data).addListener(() ->
                System.out.println("✅ Firestore write successful"), Runnable::run
        );
    }
}






