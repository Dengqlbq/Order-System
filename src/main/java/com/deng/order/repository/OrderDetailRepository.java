package com.deng.order.repository;

import com.deng.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2018/9/2
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 查询订单的详细情况
     *
     * @param orderId 订单id
     * @return 订单详情列表
     */
    List<OrderDetail> findByOrOrderId(String orderId);
}
