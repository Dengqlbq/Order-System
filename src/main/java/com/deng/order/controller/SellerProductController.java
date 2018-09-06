package com.deng.order.controller;

import com.deng.order.dataobject.ProductInfo;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.service.ProductService;
import com.deng.order.utils.MapUtil;
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
 * @description: 卖家商品
 * @author: Deng
 * @create: 2018/9/5
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    private final String REDIRECT_URL = "/order_system/seller/product/list";

    /**
     * 商品列表
     *
     * @param page 分页
     * @param size 每页数量
     * @param map map
     * @return 商品列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);

        // 当前版本PageImpl无法获取totalPages
        map.put("totalPages", productService.count() / size + 1);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);

        return new ModelAndView("product/list", map);
    }

    /**
     * 商品上架
     *
     * @param productId 商品id
     * @return 上架结果
     */
    @GetMapping("/up")
    public ModelAndView up(@RequestParam("productId") String productId) {
        try {
            productService.up(productId);
        } catch (SellException e) {
            log.error("[商品上架]: {}", e.getMessage());
            return new ModelAndView("common/error", MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView("common/success", MapUtil.redirectMap(ResultEnum.PRODUCT_UP_SUCCESS.getMessage(), REDIRECT_URL));
    }

    /**
     * 商品下架
     *
     * @param productId 商品id
     * @return 下架结果
     */
    @GetMapping("/down")
    public ModelAndView down(@RequestParam("productId") String productId) {
        try {
            productService.down(productId);
        } catch (SellException e) {
            log.error("[商品下架]: {}", e.getMessage());
            return new ModelAndView("common/error", MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView("common/success", MapUtil.redirectMap(ResultEnum.PRODUCT_DOWN_SUCCESS.getMessage(), REDIRECT_URL));
    }
}
