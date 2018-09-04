package com.deng.order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 创建订单时POST过来的FORM
 * @author: Deng
 * @create: 2018/9/3
 */
@Data
public class OrderForm {

    /** 买家姓名 */
    @NotEmpty(message = "买家姓名不能为空")
    private String name;

    /** 买家电话 */
    @NotEmpty(message = "买家电话不能为空")
    private String phone;

    /** 买家地址 */
    @NotEmpty(message = "买家地址不能为空")
    private String address;

    /** 买家微信openid */
    @NotEmpty(message = "买家微信openid不能为空")
    private String openid;

    /** 购物车 */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
