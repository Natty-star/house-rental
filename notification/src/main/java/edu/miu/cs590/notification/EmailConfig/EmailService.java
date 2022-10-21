package edu.miu.cs590.notification.EmailConfig;

public interface EmailService {
     void sendEmail (String to, String subject, String message);
        void sendHtmlEmail (String to, String subject, String message);
}
