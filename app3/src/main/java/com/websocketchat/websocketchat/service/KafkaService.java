package com.websocketchat.websocketchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public  void updateChat(ChatMessageDTO chatMessageDTO){
        try{

            kafkaTemplate.send("chat-topic",objectMapper.writeValueAsString(chatMessageDTO));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
