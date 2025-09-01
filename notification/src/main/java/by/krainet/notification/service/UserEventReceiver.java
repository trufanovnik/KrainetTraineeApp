package by.krainet.notification.service;

import by.krainet.UserEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventReceiver {

    @RabbitListener(queues = "user.events.queue")
    public void handleUserEvent(UserEvent userEvent) {
        System.out.println("Received user event: " + userEvent.getOperationType() +
                " for user: " + userEvent.getUsername() +
                " with password: " + userEvent.getPassword() +
                " with email: " + userEvent.getEmail());
    }
}
