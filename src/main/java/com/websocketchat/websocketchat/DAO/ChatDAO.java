package com.websocketchat.websocketchat.DAO;

import com.websocketchat.websocketchat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatDAO extends JpaRepository<ChatMessage,Integer> {
}
