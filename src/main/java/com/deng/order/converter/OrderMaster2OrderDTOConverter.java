package com.deng.order.converter;

import com.deng.order.dataobject.OrderMaster;
import com.deng.order.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: OrderMaser 转 OrderDTO
 * @author: Deng
 * @create: 2018/9/3
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO conver(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> conver(e)).collect(Collectors.toList());
    }
}
