package com.deng.order.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: Cookie相关
 * @author: Deng
 * @create: 2018/9/7
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response response对象
     * @param key key
     * @param value value
     * @param expire 过期时间
     */
    public static void set(HttpServletResponse response, String key, String value, Integer expire) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expire);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request request
     * @param key key
     * @return cookie(找不到返回null)
     */
    public static Cookie get(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }

        return null;
    }
}
