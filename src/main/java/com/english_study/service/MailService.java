package com.english_study.service;

import com.english_study.config.RabbitMQConfig;
import com.english_study.model.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(String to, String subject, String body) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_EXCHANGE, RabbitMQConfig.EMAIL_ROUTING_KEY, emailMessage);
        log.info("Email message pushed to RabbitMQ for: {}", to);
    }

    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify your Beelish account";
        String verificationLink = "http://localhost:5173/verify-email?token=" + token;

        String body = "<h1>Welcome to Beelish!</h1>" +
                "<p>Please click the link below to verify your email address:</p>" +
                "<a href=\"" + verificationLink + "\">Verify Email</a>" +
                "<p>If you didn't request this, you can safely ignore this email.</p>";

        sendEmail(to, subject, body);
    }

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Reset your Beelish password";
        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        String body = "<h1>Password Reset Request</h1>" +
                "<p>We received a request to reset your password. Click the link below to set a new password:</p>" +
                "<a href=\"" + resetLink + "\">Reset Password</a>" +
                "<p>If you didn't request a password reset, you can safely ignore this email.</p>";

        sendEmail(to, subject, body);
    }
}
