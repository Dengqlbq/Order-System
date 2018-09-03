package com.deng.order.service;

import com.deng.order.dataobject.ProductInfo;
import com.deng.order.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 商品
 * @author: Deng
 * @create: 2018/9/2
 */
public interface ProductService {

    /**
     * 查找一个商品
     *
     * @param productId 商品id
     * @return 商品
     */
    ProductInfo findOne(String productId);

    /**
     * 查找所有上架商品
     *
     * @return 商品列表
     */
    List<ProductInfo> findUpAll();


    /**
     * 查找所有商品
     *
     * @param page 分页
     * @return 商品列表
     */
    Page<ProductInfo> findAll(Pageable page);

    /**
     * 新增/更新商品
     *
     * @param productInfo 商品
     * @return 新增/更新后的商品
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 批量增加商品库存
     *
     * @param cartDTOList 购物车列表
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 批量减少库存
     *
     * @param cartDTOList 购物车列表
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
