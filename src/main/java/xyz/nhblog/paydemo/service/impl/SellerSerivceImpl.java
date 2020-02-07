package xyz.nhblog.paydemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.nhblog.paydemo.dataobject.SellerInfo;
import xyz.nhblog.paydemo.repository.SellerInfoRepository;
import xyz.nhblog.paydemo.service.SellerService;

@Service
public class SellerSerivceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
