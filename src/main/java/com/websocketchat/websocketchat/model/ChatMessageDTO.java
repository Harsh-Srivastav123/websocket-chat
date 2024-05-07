package com.websocketchat.websocketchat.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessageDTO {

    private MessageType type;
    private String content;
    private String sender;
    private  String groupTopic;
    private String referenceId;
}
