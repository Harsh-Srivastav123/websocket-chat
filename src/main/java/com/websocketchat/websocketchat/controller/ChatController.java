package com.websocketchat.websocketchat.controller;

import com.websocketchat.websocketchat.constants.ApplicationConstants;
import com.websocketchat.websocketchat.model.ChatMessageDTO;
import com.websocketchat.websocketchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/{groupTopic}")
    public List<ChatMessageDTO> getChats(@PathVariable String groupTopic){
        return chatService.getAllChats(groupTopic);
    }

    @PostMapping("uploadMedia")
    public ResponseEntity<String> uploadMedia(@RequestParam(name = "reference",required = true) String referenceId,
                                              @RequestParam(name = "media",required = true)MultipartFile file){
        chatService.uploadMedia(file,referenceId);
        return new ResponseEntity<>("media uploaded successfully", HttpStatus.OK);
    }

    @GetMapping("download/{key}")
    public ResponseEntity<byte[]> getObject(@PathVariable String key){

        List<Object> arrayList =chatService.downloadFile(key);
        byte[] bytes= (byte[]) arrayList.get(0);
        String fileType= String.valueOf(arrayList.get(1));
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println(fileType);
//        switch (fileType) {
//            case "application/pdf":
//                httpHeaders.setContentType(MediaType.APPLICATION_PDF);
//            case "video/mp4":
//                httpHeaders.setContentType(MediaType.valueOf("video/mp4"));
//            case "image/jpeg":
//                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//            case "audio/mpeg":
//                httpHeaders.setContentType(MediaType.valueOf("audio/mpeg"));
//            default:
//                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        }
//        httpHeaders.setContentType(MediaType.valueOf("audio/mpeg"));
        httpHeaders.setContentType(ApplicationConstants.TYPES_MAPPING.get(fileType));
        System.out.println(httpHeaders.getContentType().toString());
        httpHeaders.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
}
