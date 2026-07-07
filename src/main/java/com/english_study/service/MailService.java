package com.english_study.service;

import com.english_study.config.RabbitMQConfig;
import com.english_study.model.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private List<String> allowedOrigins;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
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
        String verificationLink = allowedOrigins.get(0) + "/verify-email?token=" + token;

        String body = "<h1>Welcome to Beelish!</h1>" +
                "<p>Please click the link below to verify your email address:</p>" +
                "<a href=\"" + verificationLink + "\">Verify Email</a>" +
                "<p>If you didn't request this, you can safely ignore this email.</p>";

        sendEmail(to, subject, body);
    }

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Reset your Beelish password";
        String resetLink = allowedOrigins.get(0) + "/reset-password?token=" + token;

        String body = "<h1>Password Reset Request</h1>" +
                "<p>We received a request to reset your password. Click the link below to set a new password:</p>" +
                "<a href=\"" + resetLink + "\">Reset Password</a>" +
                "<p>If you didn't request a password reset, you can safely ignore this email.</p>";

        sendEmail(to, subject, body);
    }
}
