package com.deng.order.controller;

import com.deng.order.dataobject.ProductCategory;
import com.deng.order.dataobject.ProductInfo;
import com.deng.order.enums.ResultEnum;
import com.deng.order.exception.SellException;
import com.deng.order.form.ProductForm;
import com.deng.order.service.CategoryService;
import com.deng.order.service.ProductService;
import com.deng.order.utils.KeyUtil;
import com.deng.order.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

    @Autowired
    private CategoryService categoryService;

    @Value("${redirect.seller.product}")
    private String REDIRECT_URL;

    @Value("${view.error}")
    private String ERROR_VIEW;

    @Value("${view.success}")
    private String SUCCESS_VIEW;

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
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView(SUCCESS_VIEW, MapUtil.redirectMap(ResultEnum.PRODUCT_UP_SUCCESS.getMessage(), REDIRECT_URL));
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
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView(SUCCESS_VIEW, MapUtil.redirectMap(ResultEnum.PRODUCT_DOWN_SUCCESS.getMessage(), REDIRECT_URL));
    }

    /**
     * 新增或修改商品
     *
     * @param productId 商品id，为空则新增，否则修改
     * @param map map
     * @return 新增或修改页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            map.put("productInfo", productService.findOne(productId));
        }

        map.put("categoryList", categoryService.findAll());
        return new ModelAndView("product/index", map);
    }

    /**
     * 根据前端POST过来的FORM新增或修改商品
     *
     * @param productForm 前端POST过来的FORM
     * @param bindingResult bindingResult
     * @return 新增或修改结果
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[新增或修改商品]: {}", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(bindingResult.getFieldError().getDefaultMessage(), REDIRECT_URL));
        }

        try {
            ProductInfo productInfo = new ProductInfo();
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = productService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }

            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (Exception e) {
            log.error("[新增或修改商品]: {}", e.getMessage());
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView(SUCCESS_VIEW, MapUtil.redirectMap(ResultEnum.PRODUCT_UPDATE_SUCCESS.getMessage(), REDIRECT_URL));
    }
}
