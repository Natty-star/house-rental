package edu.miu.cs590.reservation.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
//    @Bean
//    public NewTopic myTopic() {
//        return TopicBuilder.name("notification")
//                .partitions(3)
//                .replicas(3)
//                .build();
//    }
}
