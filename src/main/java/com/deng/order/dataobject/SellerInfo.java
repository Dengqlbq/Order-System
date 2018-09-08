package com.deng.order.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @description: 卖家信息
 * @author: Deng
 * @create: 2018/9/6
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    /** 用户名 */
    @Id
    private String username;

    /** 密码 */
    private String password;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
