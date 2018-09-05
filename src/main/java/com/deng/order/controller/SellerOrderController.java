package com.deng.order.controller;

import com.deng.order.dto.OrderDTO;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


/**
 * @description: 卖家订单
 * @author: Deng
 * @create: 2018/9/4
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

    private final String REDIRECT_URL = "/order_system/seller/order/list";

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     *
     * @param page 分页
     * @param size 每页数量
     * @param map map
     * @return 订单列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        // 当前版本PageImpl无法获取totalPages
        map.put("totalPages", orderService.count() / size + 1);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);

        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @param map map
     * @return 取消结果
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("[卖家端取消订单]: {}", e.getMessage());

            map.put("message", e.getMessage());
            map.put("redirectUrl", REDIRECT_URL);
            return new ModelAndView("common/error", map);
        }

        map.put("message", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("redirectUrl", REDIRECT_URL);
        return new ModelAndView("common/success", map);
    }

    /**
     * 查询订单详情
     *
     * @param orderId 订单id
     * @param map map
     * @return 订单详情
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("[卖家端查询订单详情]: {}", e.getMessage());

            map.put("message", e.getMessage());
            map.put("redirectUrl", REDIRECT_URL);
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("[卖家端完结订单]: {}", e.getMessage());

            map.put("message", e.getMessage());
            map.put("redirectUrl", REDIRECT_URL);
            return new ModelAndView("common/error", map);
        }

        map.put("message", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("redirectUrl", REDIRECT_URL);
        return new ModelAndView("common/success", map);
    }
}
