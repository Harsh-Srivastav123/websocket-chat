package com.websocketchat.websocketchat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Configuration
@Slf4j
public class KafkaListenerConfiguration {

    @Autowired
    SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics="chat-topic",groupId = "group-1")
    public void updateChat(String value){
        ObjectMapper mapper=new ObjectMapper();
        ChatMessageDTO chat;
        try {
            log.info(value);
            chat = mapper.readValue(value, ChatMessageDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        messagingTemplate.convertAndSend("/topic/" + chat.getGroupTopic(), chat);
    }

}
