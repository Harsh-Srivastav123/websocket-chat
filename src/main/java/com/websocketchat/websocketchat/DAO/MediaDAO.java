package com.websocketchat.websocketchat.DAO;

import com.websocketchat.websocketchat.entity.MediaMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaDAO extends JpaRepository<MediaMessage,String> {
}
