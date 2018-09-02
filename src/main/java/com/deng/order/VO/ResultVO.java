package com.deng.order.VO;


import lombok.Data;

/**
 * @description: http请求返回的最外层对象
 * @author: Deng
 * @create: 2018/9/2
 */
@Data
public class ResultVO<T> {

    /** 状态码 */
    private Integer code;

    /** 信息 */
    private String message;

    /** 具体内容 */
    private T data;
}
