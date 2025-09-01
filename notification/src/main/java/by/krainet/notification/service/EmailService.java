package by.krainet.notification.service;

import by.krainet.UserEvent;
import by.krainet.notification.config.AdminMailConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AdminMailConfig adminMailConfig;

    public void sendAdminNotification(UserEvent event) {
        String subject = "User " + event.getOperationType() + " operation";
        String content = "User " + event.getOperationType() + " operation performed.\n" +
                "Username: " + event.getUsername() + "\n" +
                "Email: " + event.getEmail() + "\n" +
                "Password: " + event.getPassword();

        for (String adminEmail : adminMailConfig.getEmails()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setSubject(subject);
            message.setText(content);

            try {
                javaMailSender.send(message);
                System.out.println("Email sent to: " + adminEmail);
            } catch (Exception e) {
                System.err.println("Failed to send email to: " + adminEmail + ", error: " + e.getMessage());
            }
        }
    }
}
