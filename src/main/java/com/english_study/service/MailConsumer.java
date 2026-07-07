package com.english_study.service;

import com.english_study.config.RabbitMQConfig;
import com.english_study.model.dto.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailConsumer {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmailMessage(EmailMessage emailMessage) {
        log.info("Received email message for: {}", emailMessage.getTo());
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(emailMessage.getTo());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getBody(), true); // true indicates HTML

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", emailMessage.getTo());
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", emailMessage.getTo(), e);
            // In a real production scenario, you might throw an AmqpRejectAndDontRequeueException 
            // or configure a Dead Letter Queue (DLQ) for failed messages.
        }
    }
}
