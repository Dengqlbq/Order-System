package com.deng.order.repository;

import com.deng.order.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2018/9/2
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 查询买家所有订单
     *
     * @param openid 买家openid
     * @param page 分页
     * @return 订单（分页后）
     */
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable page);
}
