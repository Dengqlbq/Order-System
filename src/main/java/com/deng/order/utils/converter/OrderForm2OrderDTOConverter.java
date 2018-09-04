package com.deng.order.utils.converter;

import com.deng.order.dto.CartDTO;
import com.deng.order.dto.OrderDTO;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description: OrderForm 转 OrderDTO
 * @author: Deng
 * @create: 2018/9/3
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<CartDTO> cartDTOList;
        try {
            cartDTOList = new Gson().fromJson(orderForm.getItems(), new TypeToken<List<CartDTO>>(){}.getType());
        } catch (Exception e) {
            log.error("[对象转换]: OrderForm -> OrderDTO 出错 JsonString: {}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setCartDTOList(cartDTOList);
        return orderDTO;
    }
}
