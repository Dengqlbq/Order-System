package com.deng.order.aspect;

import com.deng.order.constant.CookieConstant;
import com.deng.order.constant.RedisConstant;
import com.deng.order.exception.SellerAuthorizeException;
import com.deng.order.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 卖家权限认证（目前仅用于判断是否登陆）
 * @author: Deng
 * @create: 2018/9/7
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.deng.order.controller.Seller*.*(..))" +
    "&& !execution(public * com.deng.order.controller.SellerUserController.*(..))")
    public void verify() {}

    /**
     * 判断是否登陆
     */
    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[权限认证]: cookie中无token");
            throw new SellerAuthorizeException();
        }

        String token = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(token)) {
            log.warn("[权限认证]: redis中找不到token");
            throw new SellerAuthorizeException();
        }
    }
}
