package com.english_study.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "email.queue";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_ROUTING_KEY = "email.routing.key";

    public static final String STUDY_STAT_QUEUE = "study.stat.queue";
    public static final String STUDY_STAT_EXCHANGE = "study.stat.exchange";
    public static final String STUDY_STAT_ROUTING_KEY = "study.stat.routing.key";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Queue studyStatQueue() {
        return new Queue(STUDY_STAT_QUEUE, true);
    }

    @Bean
    public DirectExchange studyStatExchange() {
        return new DirectExchange(STUDY_STAT_EXCHANGE);
    }

    @Bean
    public Binding studyStatBinding(Queue studyStatQueue, DirectExchange studyStatExchange) {
        return BindingBuilder.bind(studyStatQueue).to(studyStatExchange).with(STUDY_STAT_ROUTING_KEY);
    }

    public static final String NOTIFICATION_BULK_QUEUE = "notification.bulk.queue";
    public static final String NOTIFICATION_BULK_EXCHANGE = "notification.bulk.exchange";
    public static final String NOTIFICATION_BULK_ROUTING_KEY = "notification.bulk.routing.key";

    @Bean
    public Queue notificationBulkQueue() {
        return new Queue(NOTIFICATION_BULK_QUEUE, true);
    }

    @Bean
    public DirectExchange notificationBulkExchange() {
        return new DirectExchange(NOTIFICATION_BULK_EXCHANGE);
    }

    @Bean
    public Binding notificationBulkBinding(Queue notificationBulkQueue, DirectExchange notificationBulkExchange) {
        return BindingBuilder.bind(notificationBulkQueue).to(notificationBulkExchange).with(NOTIFICATION_BULK_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
