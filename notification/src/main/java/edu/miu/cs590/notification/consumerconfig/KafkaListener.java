package edu.miu.cs590.notification.consumerconfig;


import edu.miu.cs590.notification.EmailConfig.EmailService;
import edu.miu.cs590.notification.EmailConfig.EmailTemplate;
import edu.miu.cs590.notification.dao.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class KafkaListener {



    @Autowired
    private EmailService emailService;


    @org.springframework.kafka.annotation.KafkaListener(topics = "notification", groupId = "group_id")

    void listen(List<NotificationRequest> notificationRequests) {


        NotificationRequest req= new RequestMapper().mapRequest(notificationRequests);

        emailService.sendHtmlEmail(req.getGustUserEmail(),"Booking Conformation",new EmailTemplate().gustEmailTemplate(req));
        emailService.sendHtmlEmail(req.getHostUserEmail(),"New Booking",new EmailTemplate().hostEmailTemplate(req));

        log.info("email sent for request ",req.getGustUserEmail(), req.getHostUserEmail());

    }


}


