package com.websocketchat.websocketchat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer messageId;
    private MessageType type;
    private String content;
    private String sender;
    private  String groupTopic;
}
