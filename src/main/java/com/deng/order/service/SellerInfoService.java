package com.deng.order.service;

/**
 * @description: 卖家信息
 * @author: Deng
 * @create: 2018/9/7
 */
public interface SellerInfoService {

    /**
     * 检查用户是否合法
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否合法
     */
    Boolean check(String username, String password);
}
