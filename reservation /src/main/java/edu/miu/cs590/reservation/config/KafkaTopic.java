package edu.miu.cs590.reservation.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

//    @Bean
//    public NewTopic testTopic(){
//        return TopicBuilder.name("reservation").build();
//    }
}
