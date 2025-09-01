package by.krainet.notification.service;

import by.krainet.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventReceiver {

    private final EmailService emailService;

    @RabbitListener(queues = "user.events.queue")
    public void handleUserEvent(UserEvent userEvent) {
        System.out.println("Received user event: " + userEvent.getOperationType() +
                " for user: " + userEvent.getUsername() +
                " with password: " + userEvent.getPassword() +
                " with email: " + userEvent.getEmail());

        emailService.sendAdminNotification(userEvent);
    }
}
