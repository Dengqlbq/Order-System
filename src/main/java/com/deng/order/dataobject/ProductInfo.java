package com.deng.order.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @description: 商品信息
 * @author: Deng
 * @create: 2018/9/2
 */
@Entity
@Data
public class ProductInfo {

    /** 商品id */
    @Id
    private String productId;

    /** 商品名 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 图片(url形式) */
    private String productIcon;

    /** 状态 0-正常，1-下架 */
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;
}
