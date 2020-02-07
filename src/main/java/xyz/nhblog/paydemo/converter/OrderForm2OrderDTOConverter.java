package xyz.nhblog.paydemo.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import xyz.nhblog.paydemo.dataobject.OrderDetail;
import xyz.nhblog.paydemo.dto.OrderDTO;
import xyz.nhblog.paydemo.enums.ResultEnum;
import xyz.nhblog.paydemo.exception.SellException;
import xyz.nhblog.paydemo.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderform) {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderform.getName());
        orderDTO.setBuyerPhone(orderform.getPhone());
        orderDTO.setBuyerAddress(orderform.getAddress());
        orderDTO.setBuyerOpenid(orderform.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(
                    orderform.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.error("对象转换错误 string={}", orderform.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }
}
