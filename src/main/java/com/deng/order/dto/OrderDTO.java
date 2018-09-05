package com.deng.order.dto;

import com.deng.order.dataobject.OrderDetail;
import com.deng.order.enums.OrderStatusEnum;
import com.deng.order.enums.PayStatusEnum;
import com.deng.order.utils.EnumUtil;
import com.deng.order.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单信息传输对象
 * @author: Deng
 * @create: 2018/9/3
 */
@Data
public class OrderDTO {

    /** 订单id */
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家手机号 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信Openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 */
    private Integer orderStatus;

    /** 订单支付状态 */
    private Integer payStatus;

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 修改时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 购物车列表 */
    @JsonIgnore
    List<CartDTO> cartDTOList;

    /**
     * 订单详情列表
     * 当初接口未考虑完全，导致获取主订单详情时没有对应字段，现在补上
     */
    List<OrderDetail> orderDetailList;

    /**
     * 用于模板渲染时获取状态的对应文字表示
     *
     * @return OrderStatusEnum
     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    /**
     * 用于模板渲染时获取状态的对应文字表示
     *
     * @return PayStatusEnum
     */
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
