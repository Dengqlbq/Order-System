package com.deng.order.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @description: 类目表
 * @author: Deng
 * @create: 2018/9/2
 */
@Entity
@Data
@DynamicUpdate
public class ProductCategory {

    /** 类目ID 主键 自增 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类目名 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 @DynamicUpdate */
    private Date createTime;

    /** 修改时间 @DynamicUpdate */
    private Date updateTime;
}
