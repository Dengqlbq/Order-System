package com.deng.order.service;

import com.deng.order.dataobject.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 类目
 * @author: Deng
 * @create: 2018/9/2
 */
public interface CategoryService {

    /**
     * 查找单个类目
     *
     * @param id 类目id
     * @return 类目
     */
    ProductCategory findOne(Integer id);

    /**
     * 查找所有类目
     *
     * @return 类目列表
     */
    List<ProductCategory> findAll();

    /**
     * 查找一组类目
     *
     * @param list 类目编号列表
     * @return 类目列表
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);

    /**
     * 更新/新增类目
     *
     * @param productCategory 类目
     * @return 更新/新增后的类目
     */
    ProductCategory save(ProductCategory productCategory);
}
