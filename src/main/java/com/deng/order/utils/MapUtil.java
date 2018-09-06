package com.deng.order.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: map的一些操作
 * @author: Deng
 * @create: 2018/9/6
 */
public class MapUtil {

    /**
     * 成功或失败页面跳转所需的map
     *
     * @param message 提示信息
     * @param redirectUrl 跳转url
     * @return map
     */
    public static Map redirectMap(String message, String redirectUrl) {
        Map<String, String> map = new HashMap<>(2);
        map.put("message", message);
        map.put("redirectUrl", redirectUrl);

        return map;
    }
}
