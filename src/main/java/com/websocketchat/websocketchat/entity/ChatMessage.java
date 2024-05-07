package com.websocketchat.websocketchat.entity;


import com.websocketchat.websocketchat.model.MessageType;
import jakarta.persistence.*;
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

//    @OneToOne(cascade = CascadeType.ALL,mappedBy = "chatMessage")
//    MediaMessage mediaMessage;
    private String referenceId;
}
