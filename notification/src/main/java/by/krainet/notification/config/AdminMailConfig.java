package by.krainet.notification.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@ConfigurationProperties(prefix = "admin")
public class AdminMailConfig {

    private List<String> emails;
}
