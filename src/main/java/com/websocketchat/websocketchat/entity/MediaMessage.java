package com.websocketchat.websocketchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaMessage {
    @Id
    String referenceId;

//    @OneToOne
//    @JoinColumn(name = "media",referencedColumnName = "mediaContentId")
//    ChatMessage chatMessage;
    String url;
    String name;
    Long size;
    Boolean status;
    String type;

}
