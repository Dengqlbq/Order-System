package com.deng.order.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @description: 订单状态
 * @author: Deng
 * @create: 2018/9/2
 */
@Getter
public enum OrderStatusEnum {

    /**
     * 新订单
     */
    NEW(0, "新订单"),

    /**
     * 订单已完结
     */
    FINISH(1, "完结"),

    /**
     * 订单已取消
     */
    CANCEL(2, "取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
