package xyz.nhblog.paydemo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.nhblog.paydemo.dataobject.SellerInfo;
import xyz.nhblog.paydemo.utils.KeyUtil;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("test");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid("abc");

        SellerInfo res = repository.save(sellerInfo);
        Assert.assertNotNull(res);
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("abc");
        Assert.assertEquals(sellerInfo.getOpenid(), "abc");
    }
}