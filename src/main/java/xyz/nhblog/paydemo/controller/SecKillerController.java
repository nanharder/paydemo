package xyz.nhblog.paydemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.nhblog.paydemo.service.SecKillService;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillerController {

    @Autowired
    private SecKillService secKillService;

    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception {
        return secKillService.querySecKillProductInfo(productId);
    }

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
        log.info("@skill request, productId:" + productId);
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }
}
