package com.english_study.service;

import com.english_study.config.RabbitMQConfig;
import com.english_study.model.dto.BulkNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_BULK_QUEUE)
    public void consumeBulkNotificationEvent(BulkNotificationEvent event) {
        log.info("Received bulk notification event: title='{}', targetType='{}'", event.getTitle(), event.getTargetType());
        try {
            notificationService.processBulkNotification(event);
            log.info("Successfully processed bulk notification event.");
        } catch (Exception e) {
            log.error("Error processing bulk notification event: ", e);
        }
    }
}
