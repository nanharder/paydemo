package xyz.nhblog.paydemo.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.nhblog.paydemo.constant.MQConstant;
import xyz.nhblog.paydemo.dto.OrderDTO;

import java.time.LocalDateTime;

@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String orderId) {
        this.rabbitTemplate.convertAndSend(MQConstant.DELAY_EXCHANGE, MQConstant.DELAY_ROUTING_KEY, orderId, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, String.class.getName());
            message.getMessageProperties().setExpiration(1800 + "");
            return message;
        });
    }
}
