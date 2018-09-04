package com.deng.order.service;

import com.deng.order.dto.OrderDTO;

/**
 * @description: 买家
 *               买家端某些操作放到这里，避免Controller逻辑过于复杂
 * @author: Deng
 * @create: 2018/9/4
 */
public interface BuyerService {

    /**
     * 查询单个订单
     *
     * @param openid 买家openid
     * @param orderId 订单id
     * @return 查询结果，查不到返回null
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     *
     * @param openid 买家openid
     * @param orderId 订单id
     * @return 取消后的OrderDTO
     */
    OrderDTO cancelOrder(String openid, String orderId);
}
