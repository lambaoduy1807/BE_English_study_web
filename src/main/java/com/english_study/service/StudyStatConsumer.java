package com.english_study.service;

import com.english_study.config.RabbitMQConfig;
import com.english_study.model.dto.StudyStatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyStatConsumer {

    private final UserDailyStatService userDailyStatService;
    private final UserStreakService userStreakService;

    @RabbitListener(queues = RabbitMQConfig.STUDY_STAT_QUEUE)
    public void consumeStudyStatEvent(StudyStatEvent event) {
        log.info("Received study stat event for user: {}", event.getUserId());
        try {
            if (event.getVocabId() != null) {
                userDailyStatService.recordDailyStat(event.getUserId(), event.getVocabId(), event.getNewWords(), event.getXpGained());
            } else if (event.getVideoId() != null) {
                userDailyStatService.recordVideoStat(event.getUserId(), event.getVideoId(), event.getNewWords(), event.getXpGained());
            }

            if (event.getNewWords() > 0 || event.getXpGained() > 0) {
                userStreakService.recordStudyDay(event.getUserId(), event.getNewWords());
            }
            log.info("Successfully processed study stat for user: {}", event.getUserId());
        } catch (Exception e) {
            log.error("Error processing study stat event for user: {}", event.getUserId(), e);
        }
    }
}
