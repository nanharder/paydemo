package xyz.nhblog.paydemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.nhblog.paydemo.exception.SellException;
import xyz.nhblog.paydemo.service.RedisLock;
import xyz.nhblog.paydemo.service.SecKillService;
import xyz.nhblog.paydemo.utils.KeyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000; //超时时间 10s

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 国庆活动，皮蛋粥特价，限量100000份
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static
    {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId)
    {
        return "国庆活动，皮蛋粥特价，限量份"
                + products.get(productId)
                +" 还剩：" + stock.get(productId)+" 份"
                +" 该商品成功下单用户数目："
                +  orders.size() +" 人" ;
    }

    @Override
    public String querySecKillProductInfo(String productId)
    {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String openid, String productId) {
        // 每个用户访问时加时间限制
        String value = redisTemplate.opsForValue().get(openid);
        if (StringUtils.isEmpty(value)) {
            String uid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(openid, uid, 1, TimeUnit.SECONDS);
            //加锁
            long time = System.currentTimeMillis() + TIMEOUT;
            if (redisLock.lock(productId, String.valueOf(time))) {
                throw new SellException(101,  "人太多了，再尝试一次吧");
            }


            //1.查询该商品库存，为0则活动结束。
            int stockNum = stock.get(productId);
            if(stockNum == 0) {
                throw new SellException(100,"活动结束");
            }else {
                //2.下单(模拟不同用户openid不同)
                orders.put(openid, productId);
                //3.减库存
                stockNum = stockNum-1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock.put(productId,stockNum);
            }

            //解锁
            redisLock.unlock(productId, String.valueOf(time));
        } else {
            throw new SellException(102, "访问太频繁了");
        }

    }
}
