package com.deng.order.service.impl;

import com.deng.order.dto.OrderDTO;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.service.BuyerService;
import com.deng.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 买家
 * @author: Deng
 * @create: 2018/9/4
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("[取消订单]: 查询不到订单 OrderId: {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderService.cancel(orderDTO);
    }

    /**
     * 判断订单是否属于当前买家
     *
     * @param openid 买家openid
     * @param orderId 订单主表id
     * @return OrderDTO
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }

        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.error("[查询订单]: 订单不属于当前用户 OrderDTO.OpenID: {} OpenId: {}", orderDTO.getBuyerOpenid(), orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }
}
