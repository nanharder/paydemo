package xyz.nhblog.paydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nhblog.paydemo.dataobject.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openid);
}
