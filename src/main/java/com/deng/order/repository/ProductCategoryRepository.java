package com.deng.order.repository;

import com.deng.order.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 商品类目
 * @author: Deng
 * @create: 2018/9/2
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 获取类目列表
     *
     * @param list 类目id列表
     * @return 类目列表
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
}
