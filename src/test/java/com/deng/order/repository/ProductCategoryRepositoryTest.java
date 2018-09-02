package com.deng.order.repository;

import com.deng.order.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findByIdTest() {
        ProductCategory productCategory;
        productCategory = repository.findById(1).get();
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory;
        productCategory = repository.findById(1).get();
        productCategory.setCategoryType(1);
        repository.save(productCategory);
    }

    /**
     * @Transactional
     * 测试方法中方法完成后全部数据回滚
     * 其他方法中发生错误时全部数据回滚
     */
    @Test
    @Transactional
    public void deleteTest() {
        repository.deleteById(1);
    }

    @Test
    public void findByCatrgoryTypeInTest() {
        List<Integer> list = Arrays.asList(1, 2);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }
}