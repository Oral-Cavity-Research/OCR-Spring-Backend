package com.oasis.ocrspring.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    @Value("${senders.email}")
    private String sendersEmail;
    @Value("${senders.pass}")
    private String sendersPass;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String receiversEmail, String type, String message, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendersEmail, sendersPass);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sendersEmail, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiversEmail));
            msg.setSubject("OCRP Account Registrations");
            msg.setContent(body(type, message, name), "text/html");
            msg.setSentDate(new java.util.Date());

            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String body(String type, String message, String name) {
        Context context = new Context();
        context.setVariable("name", name);
        switch(type) {
            case "ACCEPT":
                return templateEngine.process("emails/accept", context);


            case "REJECT":
                return templateEngine.process("emails/reject", context);
            default:
                return String.valueOf(' ');
        }
    }
}