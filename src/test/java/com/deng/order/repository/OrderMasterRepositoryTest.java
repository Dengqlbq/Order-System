package com.deng.order.repository;

import com.deng.order.dataobject.OrderMaster;
import com.deng.order.enums.OrderStatusEnum;
import com.deng.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "shegidskjf";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("5678");
        orderMaster.setBuyerName("man");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerAddress("华南");
        orderMaster.setBuyerPhone("15622222222");
        orderMaster.setOrderAmount(new BigDecimal(10));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        Assert.assertNotNull(repository.save(orderMaster));
    }

    @Test
    public void findByBuyerOpenId() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
    }
}