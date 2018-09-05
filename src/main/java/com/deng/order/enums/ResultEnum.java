package com.deng.order.enums;

import lombok.Getter;

/**
 * @description: 各种操作结果
 * @author: Deng
 * @create: 2018/9/3
 */
@Getter
public enum ResultEnum {

    /**
     *
     */
    PRODUCT_NOT_EXIST(0, "商品不存在"),

    PRODUCT_STOCK_ERROR(1, "商品库存错误"),

    ORDER_NOT_EXIST(2, "订单不存在"),

    ORDER_DETAIL_EMPTY(3, "订单详情为空"),

    ORDER_STATUS_ERROR(4, "订单状态错误"),

    ORDER_UPDATE_FAIL(5, "订单更新失败"),

    ORDER_PAY_STATUS_ERROR(6, "订单支付状态错误"),

    PARAM_ERROR(7, "参数错误"),

    CART_EMPTY(8, "购物车为空"),

    ORDER_OWNER_ERROR(9, "订单不属于当前用户"),

    ORDER_CANCEL_SUCCESS(10, "订单取消成功"),

    ORDER_FINISH_SUCCESS(11, "订单完结成功"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
