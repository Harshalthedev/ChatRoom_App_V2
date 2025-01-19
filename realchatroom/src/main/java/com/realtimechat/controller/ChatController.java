package com.realtimechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.realtimechat.model.Message;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat-room")
    public void getContent(Message message) {
        String roomId = message.getRoomId();
        messagingTemplate.convertAndSend("/topic/return-to/" + roomId, message);
    }
}
