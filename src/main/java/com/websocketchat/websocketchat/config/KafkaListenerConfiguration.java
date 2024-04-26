package com.websocketchat.websocketchat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Configuration
@Slf4j
public class KafkaListenerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaListenerConfiguration.class);
    @Autowired
    SimpMessageSendingOperations messagingTemplate;

    @Value("${kafka.groupId}")
    private String groupId;


    @KafkaListener(topics="chat-topic",groupId = "${kafka.groupId}")
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
