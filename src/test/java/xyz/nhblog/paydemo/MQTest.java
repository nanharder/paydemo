package xyz.nhblog.paydemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.nhblog.paydemo.service.OrderSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MQTest {

    @Autowired
    OrderSender orderSender;

    @Test
    public void contextLoads() {
        orderSender.send("123456");
    }
}
