package xyz.nhblog.paydemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.nhblog.paydemo.constant.MQConstant;
import xyz.nhblog.paydemo.dto.OrderDTO;
import xyz.nhblog.paydemo.enums.OrderStatusEnum;
import xyz.nhblog.paydemo.enums.PayStatusEnum;

@Component
@Slf4j
public class MQservice {

    @Autowired
    OrderService orderService;

    @RabbitListener(queues = {MQConstant.QUEUE})
    public void receive(String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) && orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            orderService.cancel(orderDTO);
            log.info("【取消订单】订单规定时间内未支付，已取消");
        }
    }
}
