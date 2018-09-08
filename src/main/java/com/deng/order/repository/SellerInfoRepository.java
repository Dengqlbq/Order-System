package com.deng.order.repository;

import com.deng.order.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 卖家信息
 * @author: Deng
 * @create: 2018/9/6
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
}
