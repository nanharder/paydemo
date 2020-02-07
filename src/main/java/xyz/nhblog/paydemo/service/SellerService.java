package xyz.nhblog.paydemo.service;

import xyz.nhblog.paydemo.dataobject.SellerInfo;

public interface SellerService {
    /**
     * 通过openid查询用户
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
