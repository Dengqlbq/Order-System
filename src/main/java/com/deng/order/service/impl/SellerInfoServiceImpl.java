package com.deng.order.service.impl;

import com.deng.order.dataobject.SellerInfo;
import com.deng.order.repository.SellerInfoRepository;
import com.deng.order.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 卖家信息
 * @author: Deng
 * @create: 2018/9/7
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public Boolean check(String username, String password) {
        SellerInfo sellerInfo = repository.findById(username).orElse(null);
        if (sellerInfo == null || !sellerInfo.getPassword().equals(password)) {
            return false;
        }

        return true;
    }
}
