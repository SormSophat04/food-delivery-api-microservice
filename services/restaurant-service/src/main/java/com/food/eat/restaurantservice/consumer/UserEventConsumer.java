package com.food.eat.restaurantservice.consumer;

import com.food.eat.restaurantservice.dto.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class UserEventConsumer {

    @KafkaListener(topics = "user-topic", groupId = "restaurant-group")
    public void handleUserEvent(UserEvent event) {
        log.info("Received user event: userId={}, username={}, email={}, enabled={}",
                event.userId(), event.username(), event.email(), event.enabled());
    }
}
