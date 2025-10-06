package com.bank.notification_service.redis;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.bank.notification_service.constants.Constants.Web_Socket_Topic_Name;

@Service
public class MessageSubscriber {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void onMessage(String message, String channel) {
        System.out.println("Received message: " + message + " from channel: " + channel);

        Map<String, String> payload = new HashMap<>();
        payload.put("message", message); // wrap inside JSON structure

        messagingTemplate.convertAndSend("/topic/notifications", payload);
    }
}