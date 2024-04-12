package com.websocketchat.websocketchat.controller;

import com.websocketchat.websocketchat.model.ChatMessageDTO;
import com.websocketchat.websocketchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/{groupTopic}")
    public List<ChatMessageDTO> getChats(@PathVariable String groupTopic){
        return chatService.getAllChats(groupTopic);
    }
}
