package com.deng.order.handler;

import com.deng.order.exception.SellException;
import com.deng.order.utils.ResultVOUtil;
import com.deng.order.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 业务逻辑统一异常处理器
 * @author: Deng
 * @create: 2018/9/8
 */
@ControllerAdvice
public class SellExceptionHandler {

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handle(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
