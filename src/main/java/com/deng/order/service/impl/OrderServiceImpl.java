package com.deng.order.service.impl;

import com.deng.order.utils.converter.OrderMaster2OrderDTOConverter;
import com.deng.order.dataobject.OrderDetail;
import com.deng.order.dataobject.OrderMaster;
import com.deng.order.dataobject.ProductInfo;
import com.deng.order.dto.CartDTO;
import com.deng.order.dto.OrderDTO;
import com.deng.order.enums.OrderStatusEnum;
import com.deng.order.enums.PayStatusEnum;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.repository.OrderDetailRepository;
import com.deng.order.repository.OrderMasterRepository;
import com.deng.order.service.OrderService;
import com.deng.order.service.ProductService;
import com.deng.order.utils.KeyUtil;
import com.deng.order.websocket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 订单
 * @author: Deng
 * @create: 2018/9/3
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        final String ORDER_ID = KeyUtil.genUniqueKey();

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<CartDTO> cartDTOList = orderDTO.getCartDTOList();

        // 1. 计算商品总价及订单详情入库
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productService.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                log.error("[创建订单]: 商品不存在 productID: {}", cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(cartDTO.getProductQuantity())).add(orderAmount);

            // 订单详情入库
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(ORDER_ID);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setProductQuantity(cartDTO.getProductQuantity());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // 2. 订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(ORDER_ID);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);

        // 3. 扣库存
        productService.decreaseStock(cartDTOList);

        // 4. 向后台页面发送消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            log.error("[查询订单]: 订单不存在 OrderId: {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("[查询订单]: 订单详情为空 OrderId: {}", orderId);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setCartDTOList(cartDTOList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable page) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, page);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, page, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable page) {
        Page<OrderMaster> orderMasterList = orderMasterRepository.findAll(page);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList.getContent());
        return new PageImpl<>(orderDTOList, page, orderDTOList.size());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单]: 订单状态错误 OrderId: {} OrderStatus: {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if (orderMasterRepository.save(orderMaster) == null) {
            log.error("[取消订单]: 订单更新失败 OrderMaster: {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 3. 库存返还
        if (CollectionUtils.isEmpty(orderDTO.getCartDTOList())) {
            log.error("[取消订单]: 订单详情为空 OrderDTO: {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getCartDTOList();
        productService.increaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单]: 订单状态错误 OrderId: {} OrderStatus: {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if (orderMasterRepository.save(orderMaster) == null) {
            log.error("[完结订单]: 订单更新失败 OrderMaster: {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[支付订单]: 订单状态错误 OrderId: {} OrderStatus: {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 2. 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[支付订单]: 订单支付状态错误 PayStatus: {}", orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 3. 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        if (orderMasterRepository.save(orderMaster) == null) {
            log.error("[支付订单]: 订单更新失败 OrderMaster: {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public long count() {
        return orderMasterRepository.count();
    }
}
