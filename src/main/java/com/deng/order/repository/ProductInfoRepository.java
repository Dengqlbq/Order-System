package com.deng.order.repository;

import com.deng.order.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 商品信息
 * @author: Deng
 * @create: 2018/9/2
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 查询指定状态所有商品
     *
     * @param status 商品状态
     * @return 商品列表
     */
    List<ProductInfo> findByProductStatus(Integer status);

}
