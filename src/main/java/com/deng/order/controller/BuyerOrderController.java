package com.deng.order.controller;

import com.deng.order.service.BuyerService;
import com.deng.order.utils.converter.OrderForm2OrderDTOConverter;
import com.deng.order.dto.OrderDTO;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.form.OrderForm;
import com.deng.order.service.OrderService;
import com.deng.order.utils.ResultVOUtil;
import com.deng.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 买家订单
 * @author: Deng
 * @create: 2018/9/3
 */
@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 根据前端传过来的表单创建订单
     *
     * @param orderForm 前端传过来的表单
     * @param bindingResult 参数校验
     * @return 创建结果
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单]: 参数错误 OrderForm: {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getCartDTOList())) {
            log.error("[创建订单]: 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);
    }

    /**
     * 根据openid查询订单
     *
     * @param openid 买家openid
     * @param page 分页
     * @param size 每页数量
     * @return 订单列表
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表]: Openid不能为空 Openid: {}", openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 查询订单详情
     *
     * @param openid 用户openid
     * @param orderId 订单主表id
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("[查询订单详情]: Openid与OrderId不能为空 Openid: {} OrderId: {}", openid, orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     *
     * @param openid 用户openid
     * @param orderId 订单主表id
     * @return 取消结果
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("[取消订单]: Openid与OrderId不能为空 Openid: {} OrderId: {}", openid, orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        buyerService.cancelOrder(openid, orderId);

        return ResultVOUtil.success();
    }
}
