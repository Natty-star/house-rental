package edu.miu.cs590.notification.EmailConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class ServiceImpl implements EmailService{


    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(String to, String subject, String message)  {
        SimpleMailMessage notificationEmail = new SimpleMailMessage();
        notificationEmail.setFrom("pmanagement.cs545@gmail.com");
        notificationEmail.setSubject(subject);
        notificationEmail.setText(message);
        notificationEmail.setTo(to);

        sender.send(notificationEmail);


    }

    @Override
    public void sendHtmlEmail(String to, String subject, String message)  {
        boolean html = true;

        MimeMessage zmessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(zmessage);

        try {
            helper.setSubject(subject);
            helper.setFrom("pmanagement.cs545@gmail.com");
            helper.setTo(to);
            helper.setText(message, html);

            sender.send(zmessage);
            log.info("Email sent successfully");

        } catch (MessagingException e) {

            log.error("error occurred while sending email");
            throw new RuntimeException(e);
        }










    }




}
