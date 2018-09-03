package com.deng.order.utils;

import java.util.Random;

/**
 * @description: Key统一生成类
 * @author: Deng
 * @create: 2018/9/3
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间 + 随机数
     *
     * @return 主键
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(a);
    }
}
