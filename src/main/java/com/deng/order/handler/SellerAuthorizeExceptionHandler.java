package com.deng.order.handler;

import com.deng.order.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 卖家权限认证异常处理器
 * @author: Deng
 * @create: 2018/9/7
 */
@ControllerAdvice
public class SellerAuthorizeExceptionHandler {

    @Value("${redirect.seller.aspect.login.fail}")
    private String LOGIN_FAIL;

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handle() {
        return new ModelAndView("redirect:" + LOGIN_FAIL);
    }
}
