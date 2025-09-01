package by.krainet.auth.service;

import by.krainet.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendUserEvent(UserEvent userEvent) {
        rabbitTemplate.convertAndSend(
                "user.events.exchange",
                "user.event.routingKey",
                userEvent
        );
        System.out.println("Sent user event: " + userEvent.getOperationType() + " for user: " + userEvent.getUsername());
    }
}
