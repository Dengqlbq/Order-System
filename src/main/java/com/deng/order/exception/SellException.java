package com.deng.order.exception;

import com.deng.order.enums.ResultEnum;
import lombok.Getter;

/**
 * @description: 业务逻辑统一异常
 * @author: Deng
 * @create: 2018/9/3
 */
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
