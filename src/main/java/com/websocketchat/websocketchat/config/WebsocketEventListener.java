package com.websocketchat.websocketchat.config;

import com.websocketchat.websocketchat.model.ChatMessage;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import com.websocketchat.websocketchat.model.MessageType;
import com.websocketchat.websocketchat.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Slf4j
@Configuration
public class WebsocketEventListener {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    KafkaService kafkaService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String groupTopic = (String) headerAccessor.getSessionAttributes().get("groupTopic");
        if (username != null) {
            log.info("user disconnected: {}", username);
            ChatMessageDTO chatMessage = ChatMessageDTO.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .groupTopic(groupTopic)
                    .build();
            log.info("/topic/"+groupTopic);
//            messagingTemplate.convertAndSend("/topic/"+groupTopic, chatMessage);
            kafkaService.updateChat(chatMessage);
        }
    }
}
