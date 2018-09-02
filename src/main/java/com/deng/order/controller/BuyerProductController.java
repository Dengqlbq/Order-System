package com.deng.order.controller;

import com.deng.order.vo.ProductInfoVO;
import com.deng.order.vo.ProductVO;
import com.deng.order.vo.ResultVO;
import com.deng.order.dataobject.ProductCategory;
import com.deng.order.dataobject.ProductInfo;
import com.deng.order.service.CategoryService;
import com.deng.order.service.ProductService;
import com.deng.order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 买家商品
 * @author: Deng
 * @create: 2018/9/2
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 按分类返回所有上架商品
     *
     * @return ResultVO
     */
    @GetMapping("/list")
    public ResultVO list() {
        // 1. 查询所有上架商品
        List<ProductInfo> productInfos = productService.findUpAll();

        // 2. 查询类目
        List<Integer> productCategoryTypes = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(productCategoryTypes);

        // 3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo info : productInfos) {
                if (info.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(info, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
