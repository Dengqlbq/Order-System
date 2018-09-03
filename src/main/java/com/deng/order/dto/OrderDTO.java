package com.deng.order.dto;

import com.deng.order.dataobject.OrderDetail;
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
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 购物车列表 */
    List<CartDTO> cartDTOList;
}
