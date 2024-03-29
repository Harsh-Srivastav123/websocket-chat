package com.websocketchat.websocketchat.DAO;

import com.websocketchat.websocketchat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatDAO extends JpaRepository<ChatMessage,Integer> {
    List<ChatMessage> findByGroupTopic(String groupTopic);
}
