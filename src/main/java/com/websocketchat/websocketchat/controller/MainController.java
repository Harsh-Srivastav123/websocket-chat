package com.websocketchat.websocketchat.controller;

import com.websocketchat.websocketchat.model.ChatMessage;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import com.websocketchat.websocketchat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class MainController {

    @Autowired
    ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(
            @Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor
    ) {
        log.info(headerAccessor.getSessionAttributes().get("username").toString());
        return chatService.addChat(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(
            @Payload ChatMessageDTO chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatService.addChat(chatMessage);


    }

}
