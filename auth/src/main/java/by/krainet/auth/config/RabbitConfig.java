package by.krainet.auth.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange userEventExchange() {
        return new TopicExchange("user.events.exchange");
    }
}
