package com.deng.order.utils;

import com.deng.order.enums.CodeEnum;

/**
 * @description: 通过Code值获取相应Enum
 * @author: Deng
 * @create: 2018/9/4
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T constant : enumClass.getEnumConstants()) {
            if (code.equals(constant.getCode())) {
                return constant;
            }
        }
        return null;
    }
}
