package com.deng.order.controller;

import com.deng.order.dataobject.ProductCategory;
import com.deng.order.enums.ResultEnum;
import com.deng.order.form.CategoryForm;
import com.deng.order.service.CategoryService;
import com.deng.order.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * @description: 卖家类目
 * @author: Deng
 * @create: 2018/9/6
 */
@Slf4j
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Value("${redirect.seller.category}")
    private String REDIRECT_URL;

    @Value("${view.success}")
    private String SUCCESS_VIEW;

    @Value("${view.error}")
    private String ERROR_VIEW;

    /**
     * 查询分类列表
     *
     * @param map map
     * @return 所有分类（不分页）
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        map.put("categoryList", categoryService.findAll());
        return new ModelAndView("category/list", map);
    }

    /**
     * 修改或新增类目
     *
     * @param categoryId 类目id（为空则为新增，否则修改）
     * @param map map
     * @return 修改或新增类目页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            map.put("category", categoryService.findOne(categoryId));
        }

        return new ModelAndView("category/index", map);
    }

    /**
     * 根据前端POST过来的FORM新增或修改类目
     *
     * @param categoryForm 前端POST过来的FORM
     * @param bindingResult bindingResult
     * @return 新增或修改结果
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[修改或新增类目]: {}", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(bindingResult.getFieldError().getDefaultMessage(), REDIRECT_URL));
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
        } catch (Exception e) {
            log.error("[修改或新增类目]: {}", e.getMessage());
            return new ModelAndView(ERROR_VIEW, MapUtil.redirectMap(e.getMessage(), REDIRECT_URL));
        }

        return new ModelAndView(SUCCESS_VIEW, MapUtil.redirectMap(ResultEnum.CATEGORY_UPDATE_SUCCESS.getMessage(), REDIRECT_URL));
    }
}
