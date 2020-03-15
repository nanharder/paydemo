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

    public void verifyPaid(String orderId) {
        this.rabbitTemplate.convertAndSend(MQConstant.DELAY_EXCHANGE, MQConstant.DELAY_ROUTING_KEY, orderId, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, String.class.getName());
            message.getMessageProperties().setExpiration(1800 + "");
            return message;
        });
    }

    public void sendOrder(OrderDTO orderDTO) {
        this.rabbitTemplate.convertAndSend(MQConstant.ORDER_EXCHANGE, MQConstant.NEW_ROUTING_KEY, orderDTO, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, OrderDTO.class.getName());
            return message;
        });
    }

    public void cancelOrder(OrderDTO orderDTO) {
        this.rabbitTemplate.convertAndSend(MQConstant.ORDER_EXCHANGE, MQConstant.CANCEL_ROUTING_KEY, orderDTO, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, OrderDTO.class.getName());
            return message;
        });
    }
}
