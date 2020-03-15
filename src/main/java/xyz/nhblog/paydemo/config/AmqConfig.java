package xyz.nhblog.paydemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.nhblog.paydemo.constant.MQConstant;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class AmqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause)));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

    @Bean
    public Queue delayProcessQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", MQConstant.EXCHANGE);
        params.put("x-dead-letter-routing-key", MQConstant.ROUTING_KEY);
        return new Queue(MQConstant.DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(MQConstant.DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayProcessQueue()).to(delayExchange()).with(MQConstant.DELAY_ROUTING_KEY);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(MQConstant.QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MQConstant.EXCHANGE);
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(topicExchange()).with(MQConstant.ROUTING_KEY);
    }
}
