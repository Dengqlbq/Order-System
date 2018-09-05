package com.deng.order.service;


import com.deng.order.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @description: 订单
 * @author: Deng
 * @create: 2018/9/3
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     *
     * @param orderId 订单id
     * @return
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询用户订单
     *
     * @param buyerOpenid 用户Openid
     * @param page 分页
     * @return 订单列表
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable page);

    /**
     * 查询所有订单
     *
     * @param page 分页
     * @return 订单列表
     */
    Page<OrderDTO> findList(Pageable page);

    /**
     * 取消订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * 获取订单总数
     * 由于当前版本PageImpl无法直接获取totalPages
     * 则获取总数后除以传过来的size即为总页数
     *
     * @return 订单总数
     */
    long count();
}