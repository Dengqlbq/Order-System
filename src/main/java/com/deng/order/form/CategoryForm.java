package com.deng.order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 修改或新增类目时POST过来的FORM
 * @author: Deng
 * @create: 2018/9/6
 */
@Data
public class CategoryForm {

    /** 类目id */
    private Integer categoryId;

    /** 类目名 */
    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;

    /** 类目编号 */
    @NotNull(message = "类目类型不能为空")
    private Integer categoryType;
}
