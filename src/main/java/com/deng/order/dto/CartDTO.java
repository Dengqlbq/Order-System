package com.deng.order.dto;


import lombok.Data;

/**
 * @description: 购物车
 *               对应接口: order/buyer/order/create
 *               对应字段: [items]
 * @author: Deng
 * @create: 2018/9/3
 */
@Data
public class CartDTO {

    /** 商品id */
    private String productId;

    /** 商品数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
