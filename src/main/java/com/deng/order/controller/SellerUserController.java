package com.deng.order.controller;

import com.deng.order.constant.CookieConstant;
import com.deng.order.constant.RedisConstant;
import com.deng.order.enums.ResultEnum;
import com.deng.order.service.SellerInfoService;
import com.deng.order.utils.CookieUtil;
import com.deng.order.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 卖家用户
 * @author: Deng
 * @create: 2018/9/6
 */
@Controller
@RequestMapping("/seller/user")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redirect.seller.user.login.fail}")
    private String LOGIN_FAIL;

    @Value("${redirect.seller.user.login.success}")
    private String LOGIN_SUCCESS;

    @Value("${view.success}")
    private String SUCCESS_VIEW;

    @Value("${view.error}")
    private String ERROR_VIEW;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("user/login");
    }

    /**
     * 卖家登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param response response
     * @return 结果页面（成功: 订单列表，失败: 错误页面->登陆页面）
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse response) {
        // 1. 检查登陆信息
        if (!sellerInfoService.check(username, password)) {
            log.error("[登陆]: {}", ResultEnum.LOGIN_FAIL.getMessage());
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(ResultEnum.LOGIN_FAIL.getMessage(), LOGIN_FAIL));
        }

        // 2. 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), username, expire, TimeUnit.SECONDS);

        // 3. 设置cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return new ModelAndView("redirect:" + LOGIN_SUCCESS);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        // 1. 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 2. 清除redis的token
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        return new ModelAndView(SUCCESS_VIEW, MapUtil.redirectMap(ResultEnum.LOGOUT_SUCCESS.getMessage(), LOGIN_FAIL));
    }
}
