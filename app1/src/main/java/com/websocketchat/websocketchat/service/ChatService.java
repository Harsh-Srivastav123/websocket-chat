package com.websocketchat.websocketchat.service;

import com.websocketchat.websocketchat.DAO.ChatDAO;
import com.websocketchat.websocketchat.model.ChatMessage;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    ChatDAO chatDAO;

    @Autowired
    ModelMapper modelMapper;
    public ChatMessageDTO addChat(ChatMessageDTO chatMessage) {
        return  modelMapper.map(chatDAO.save(modelMapper.map(chatMessage,ChatMessage.class)),ChatMessageDTO.class);
    }

    public List<ChatMessageDTO> getAllChats(String groupTopic) {

        return chatDAO.findByGroupTopic(groupTopic).stream().map(object->modelMapper.map(object,ChatMessageDTO.class)).collect(Collectors.toList());
    }
}
