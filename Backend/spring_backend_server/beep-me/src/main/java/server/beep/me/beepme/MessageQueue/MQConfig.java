package server.beep.me.beepme.MessageQueue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String ROUTING_KEY = "beep-me";
    public static final String ROUTING_KEY_NOTIFICATION = "beep-me-notification";
    public static final String ROUTING_KEY_OLD_ORDERS = "old_orders";
    public static final String EXCHANGE = "message_exchange";
    public static final String QUEUE = "orders";
    public static final String NOTIFICATIONS = "notifications";
    public static final String OLD_ORDERS = "old_orders";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATIONS);
    }

    @Bean
    public Queue oldOrdersQueue() {
        return new Queue(OLD_ORDERS);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }
    
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingNotifications(@Qualifier("notificationQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(ROUTING_KEY_NOTIFICATION);
    }

    @Bean
    public Binding bindingOldOrders(@Qualifier("oldOrdersQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(ROUTING_KEY_OLD_ORDERS);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean 
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
