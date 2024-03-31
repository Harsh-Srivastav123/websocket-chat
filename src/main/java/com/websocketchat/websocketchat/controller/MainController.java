package com.websocketchat.websocketchat.controller;

import com.websocketchat.websocketchat.model.ChatMessageDTO;
import com.websocketchat.websocketchat.model.MessageType;
import com.websocketchat.websocketchat.service.ChatService;
import com.websocketchat.websocketchat.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MainController {

    @Autowired
    ChatService chatService;

    @Autowired
    SimpMessageSendingOperations messagingTemplate;

    @Autowired
    KafkaService kafkaService;


//    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//    String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if (username != null) {
//        log.info("user disconnected: {}", username);
//        ChatMessageDTO chatMessage = ChatMessageDTO.builder()
//                .type(MessageType.LEAVE)
//                .sender(username)
//                .build();
//        messagingTemplate.convertAndSend("/topic/public", chatMessage);
//    }

    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
    public void sendMessage(
            @Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor
    ) {
        log.info(chatMessage.toString());
        String groupTopic;
        if (chatMessage.getGroupTopic() == null) {
            groupTopic = "public";
        } else {
            groupTopic = chatMessage.getGroupTopic();
        }
        log.info(headerAccessor.getSessionAttributes().get("username").toString());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ChatMessageDTO chat = ChatMessageDTO.builder()
                    .type(MessageType.CHAT)
                    .sender(username)
                    .content(chatMessage.getContent())
                    .groupTopic(groupTopic)
                    .build();
//            messagingTemplate.convertAndSend("/topic/" + groupTopic, chat);
            kafkaService.updateChat(chat);
        }


        chatService.addChat(chatMessage);
    }

    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
    public void addUser(
            @Payload ChatMessageDTO chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        log.info(chatMessage.toString());
        // Add username in web socket session

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("groupTopic", chatMessage.getGroupTopic());

        String groupTopic;
        if (chatMessage.getGroupTopic() == null) {
            groupTopic = "public";
        } else {
            groupTopic = chatMessage.getGroupTopic();
        }
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
//            log.info("user disconnected: {}", username);
            ChatMessageDTO chat = ChatMessageDTO.builder()
                    .type(MessageType.JOIN)
                    .sender(username)
                    .groupTopic(groupTopic)
                    .build();
//            messagingTemplate.convertAndSend("/topic/" + groupTopic, chatMessage);
            kafkaService.updateChat(chat);
        }

        chatService.addChat(chatMessage);


    }

}
