package com.deng.order.enums;


import lombok.Getter;

/**
 * @description: 商品状态
 * @author: Deng
 * @create: 2018/9/2
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{

    /**
     *
     */
    UP(0, "商品上架"),

    DOWN(1, "商品下架");

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
