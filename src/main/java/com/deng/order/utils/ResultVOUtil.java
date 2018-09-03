package com.deng.order.utils;

import com.deng.order.vo.ResultVO;

/**
 * @description: http请求返回的最外层对象Util
 * @author: Deng
 * @create: 2018/9/2
 */
public class ResultVOUtil {

    public static ResultVO success(Object obj) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("success");
        resultVO.setData(obj);

        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);

        return resultVO;
    }
}
