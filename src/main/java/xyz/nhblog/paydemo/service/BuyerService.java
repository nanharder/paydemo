package xyz.nhblog.paydemo.service;

import xyz.nhblog.paydemo.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
