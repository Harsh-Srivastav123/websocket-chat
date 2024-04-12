package com.websocketchat.websocketchat.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfiguration {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("chat-topic").build();
    }
}
