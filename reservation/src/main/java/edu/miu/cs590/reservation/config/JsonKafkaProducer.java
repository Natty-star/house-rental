package edu.miu.cs590.reservation.config;
import edu.miu.cs590.reservation.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class JsonKafkaProducer {
    private KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String,NotificationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendNotification(NotificationRequest notificationRequest){

        Message<NotificationRequest> message = MessageBuilder
                .withPayload(notificationRequest)
                .setHeader(KafkaHeaders.TOPIC, "notification")
                .build();

        log.info("sending notification {}",notificationRequest);

        kafkaTemplate.send(message);

    }
}
