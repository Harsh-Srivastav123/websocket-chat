package com.websocketchat.websocketchat.service;

import com.websocketchat.websocketchat.DAO.ChatDAO;
import com.websocketchat.websocketchat.DAO.MediaDAO;
import com.websocketchat.websocketchat.entity.ChatMessage;
import com.websocketchat.websocketchat.entity.MediaMessage;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {

    @Autowired
    ChatDAO chatDAO;


    @Autowired
    MediaDAO mediaDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AwsServices awsServices;

    public ChatMessageDTO addChat(ChatMessageDTO chatMessage) {
        log.info(chatMessage.toString());

//       try{
//           ChatMessage chatMessage1=modelMapper.map(chatMessage,ChatMessage.class);
//           ChatMessage chatMessage2=chatDAO.save(chatMessage1);
//
//           return modelMapper.map(chatMessage2,ChatMessageDTO.class);
//       }
//       catch (Exception e){
//           e.printStackTrace();
//           throw new RuntimeException(e);
//       }

        ChatMessage chatMessage1=new ChatMessage();
        chatMessage1.setContent(chatMessage.getContent());
        chatMessage1.setType(chatMessage.getType());
        chatMessage1.setReferenceId(chatMessage.getReferenceId());
        chatMessage1.setGroupTopic(chatMessage.getGroupTopic());
        chatMessage1.setSender(chatMessage.getSender());
        chatDAO.save(chatMessage1);
        return chatMessage;
    }
//TODO chat saving logic will move to dyanmo db part batch saving
    public List<ChatMessageDTO> getAllChats(String groupTopic) {

        return chatDAO.findByGroupTopic(groupTopic).stream().map(object->modelMapper.map(object,ChatMessageDTO.class)).collect(Collectors.toList());
    }

    public void uploadMedia(MultipartFile file, String referenceId) {



        String fileName= UUID.randomUUID().toString()+"_"+file.getName();
        MediaMessage mediaMessage=new MediaMessage(referenceId,null,fileName,file.getSize(),false,file.getContentType());
        mediaDAO.save(mediaMessage);
        String url= awsServices.uploadMedia(file, fileName);
        MediaMessage message=mediaDAO.findById(referenceId).get();
        message.setUrl(url);
        message.setStatus(true);
        mediaDAO.save(message);
    }

    @SneakyThrows
    public  List<Object> downloadFile(String referenceId) {
        if(!mediaDAO.existsById(referenceId)){
           throw new Exception("File not found");
        }
        MediaMessage mediaMessage=mediaDAO.findById(referenceId).get();
        byte[] bytes=awsServices.downloadFile(mediaMessage.getName());
        List<Object> objectList=new ArrayList<>();
        objectList.add(bytes);
        objectList.add(mediaMessage.getType());
        return objectList;
    }
}
