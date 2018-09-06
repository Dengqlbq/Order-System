package com.deng.order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: 修改或新增商品时POST过来的FORM
 * @author: Deng
 * @create: 2018/9/6
 */
@Data
public class ProductForm {

    /** 商品id */
    private String productId;

    /** 商品名 */
    @NotEmpty(message = "商品名不能为空")
    private String productName;

    /** 单价 */
    @NotNull(message = "商品单价不能为空")
    private BigDecimal productPrice;

    /**
     * 商品库存
     * 对于Integer、Long类型的属性值，用的注解应该是用@NotNull，不能用@NotBlank或者@NotEmpty
     */
    @NotNull(message = "商品库存不能为空")
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 图片(url形式) */
    private String productIcon;

    /** 类目编号 */
    @NotNull(message = "商品类目不能为空")
    private Integer categoryType;
}
