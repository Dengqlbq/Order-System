package com.deng.order.enums;

import lombok.Getter;

/**
 * @description: 支付状态
 * @author: Deng
 * @create: 2018/9/2
 */
@Getter
public enum PayStatusEnum implements CodeEnum{

    /**
     *
     */
    WAIT(0, "等待支付"),

    SUCCESS(1, "支付成功");

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
