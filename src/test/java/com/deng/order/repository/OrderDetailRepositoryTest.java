package com.deng.order.repository;

import com.deng.order.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("45454");
        orderDetail.setOrderId("shegidskjf");
        orderDetail.setProductId("4534");
        orderDetail.setProductName("好吃");
        orderDetail.setProductPrice(new BigDecimal(20));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("http:xxx.png");

        Assert.assertNotNull(repository.save(orderDetail));
    }

    @Test
    public void findByOrOrderId() {
        List<OrderDetail> result = repository.findByOrOrderId("shegidskjf");
        Assert.assertNotEquals(0, result.size());
    }
}