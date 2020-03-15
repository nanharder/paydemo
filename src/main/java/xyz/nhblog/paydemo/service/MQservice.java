package xyz.nhblog.paydemo.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.nhblog.paydemo.constant.MQConstant;
import xyz.nhblog.paydemo.dto.CartDTO;
import xyz.nhblog.paydemo.dto.OrderDTO;
import xyz.nhblog.paydemo.enums.OrderStatusEnum;
import xyz.nhblog.paydemo.enums.PayStatusEnum;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MQservice {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    RankService rankService;

    @RabbitListener(queues = {MQConstant.VERIFY_QUEUE})
    public void receive(String orderId, Message message, Channel channel) throws IOException {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) && orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
                orderService.cancel(orderDTO);
                log.info("【验证支付】订单规定时间内未支付，已取消");
            }
        } catch (Exception e) {
            log.error("【验证支付】验证支付异常，{}", e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }
    }

    @RabbitListener(queues = {MQConstant.NEW_QUEUE})
    public void increaseStock(OrderDTO orderDTO, Message message, Channel channel) throws IOException {
        try {
            List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                    .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                    .collect(Collectors.toList());
            productService.decreaseStock(cartDTOList);
            rankService.addSales(cartDTOList);
        } catch (Exception e) {
            log.error("【扣除库存】扣除库存异常，{}", e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }
    }

    @RabbitListener(queues = {MQConstant.CANCEL_QUEUE})
    public void cancelOrder(OrderDTO orderDTO, Message message, Channel channel) throws IOException {
        try {
            List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                    .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                    .collect(Collectors.toList());
            productService.increaseStock(cartDTOList);
            rankService.subSales(cartDTOList);

            //如果已支付，需要退款
            if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
                //TODO
            }
        } catch (Exception e) {
            log.error("【恢复库存】恢复库存异常，{}", e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }
    }
}
