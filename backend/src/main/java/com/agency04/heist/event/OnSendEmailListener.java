package com.agency04.heist.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnSendEmailListener {
    private static final Logger LOG = LoggerFactory.getLogger(OnSendEmailListener.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String sender;

    @EventListener
    @Async
    public void handleSendMailEvent(SendEmailEvent sendEmailEvent) {
        String recipientAddress = sendEmailEvent.getRecipientAddresses();
        String subject = sendEmailEvent.getSubject();
        String message = sendEmailEvent.getMessage();

        LOG.debug("handleSendMailEvent: recipientAddress={}, subject={}, message={}", recipientAddress, subject, message);

        SimpleMailMessage email = createEmailMessage(recipientAddress, subject, message);
        try {
            mailSender.send(email);
        }catch (MailSendException e){
            LOG.error("Failed to send email : {}", e.getMessage());
        }
    }

    private SimpleMailMessage createEmailMessage(String recipientAddress, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(sender);
        return email;
    }
}
