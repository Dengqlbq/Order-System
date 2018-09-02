package com.deng.order.service.impl;

import com.deng.order.dataobject.ProductInfo;
import com.deng.order.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123");
        Assert.assertEquals("123", productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productService.findUpAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> result = productService.findAll(pageRequest);
        Assert.assertEquals(1, result.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = productService.findOne("456");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        Assert.assertNotNull(productService.save(productInfo));
    }
}