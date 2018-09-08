package com.deng.order.service.impl;

import com.deng.order.service.SellerInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoServiceImplTest {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Test
    public void check() {
        Assert.assertTrue(sellerInfoService.check("woman", "hehe"));
    }
}