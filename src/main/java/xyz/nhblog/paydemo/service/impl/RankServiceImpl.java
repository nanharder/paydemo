package xyz.nhblog.paydemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.nhblog.paydemo.dataobject.OrderDetail;
import xyz.nhblog.paydemo.dataobject.ProductInfo;
import xyz.nhblog.paydemo.dto.CartDTO;
import xyz.nhblog.paydemo.service.ProductService;
import xyz.nhblog.paydemo.service.RankService;
import xyz.nhblog.paydemo.utils.KeyUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public boolean addSales(List<CartDTO> cartDTOList) {
        String key = KeyUtil.getMonthKey();
        //redis储存销量
        for (CartDTO cartDTO: cartDTOList) {
            String value = cartDTO.getProductId();
            double sales = cartDTO.getProductQuantity();
            Double score = redisTemplate.opsForZSet().score(key, value);
            if (score == null) {
                score = (double) -sales;
            } else {
                score -= (double) sales;
            }
            redisTemplate.opsForZSet().add(key, value, score);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean subSales(List<CartDTO> cartDTOList) {
        String key = KeyUtil.getMonthKey();
        for (CartDTO cartDTO: cartDTOList) {
            String value = cartDTO.getProductId();
            double sales = cartDTO.getProductQuantity();
            Double score = redisTemplate.opsForZSet().score(key, value);
            if (score != null) {
                score += (double) sales;
                redisTemplate.opsForZSet().add(key, value, score);
            }
        }
        return true;
    }

    @Override
    public List<ProductInfo> getRank() {
        String key = KeyUtil.getMonthKey();
        Set<String> productIdSet = redisTemplate.opsForZSet().range(key, 0, -1);
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (String productId: productIdSet) {
            ProductInfo productInfo = productService.findOne(productId);
            productInfoList.add(productInfo);
            if (productInfoList.size() >= 10) {
                break;
            }
        }
        return productInfoList;
    }
}
