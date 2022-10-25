package edu.miu.cs590.notification.consumerconfig;


import edu.miu.cs590.notification.EmailConfig.EmailService;
import edu.miu.cs590.notification.EmailConfig.EmailTemplate;
import edu.miu.cs590.notification.dao.LogObject;
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
    // for notification log
    @org.springframework.kafka.annotation.KafkaListener(topics = "notification", groupId = "group_id")
    void listen(List<NotificationRequest> notificationRequests) {

        log.info("Received Message from topic : notification  " + notificationRequests);

        NotificationRequest req= new RequestMapper().mapRequest(notificationRequests);

        emailService.sendHtmlEmail(req.getGustUserEmail(),"Booking Conformation",new EmailTemplate().gustEmailTemplate(req));
        emailService.sendHtmlEmail(req.getHostUserEmail(),"New Booking",new EmailTemplate().hostEmailTemplate(req));

      log.info("email sent for host ",req.getHostUserEmail());
      log.info("email sent for gust ",req.getGustUserEmail());

    }

// for topic Payment log
    @org.springframework.kafka.annotation.KafkaListener(topics = "paymentLog", groupId = "group_id")
    void listenforPaymentlog(List<LogObject> logObject) {


        log.info("Received Message from topic : paymentlog  " + logObject);

        LogObject logObject1 = new RequestMapper().logObjectMapper(logObject);


        if (logObject1.getStatus().equals("success")) {
            emailService.sendHtmlEmail(logObject1.getEmail(), "Payment Successful", new EmailTemplate().acceptedEmailTemplate(logObject1));
            log.info("email sent to {}", logObject1.getEmail());
        } else {
            emailService.sendHtmlEmail(logObject1.getEmail(), "Payment Failed", new EmailTemplate().rejectedTemplate());
            log.info("email sent to {}", logObject1.getEmail());
        }


    }


}


