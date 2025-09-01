package by.krainet.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange("user.events.exchange");
    }

    @Bean
    public Queue userEventsQueue() {
        return new Queue("user.events.queue");
    }

    @Bean
    public Binding binding(Queue userEventsQueue, TopicExchange userEventsExchange) {
        return BindingBuilder.bind(userEventsQueue)
                .to(userEventsExchange)
                .with("user.event.#");
    }
}
