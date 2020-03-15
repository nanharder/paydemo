package xyz.nhblog.paydemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRabbit
public class PaydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaydemoApplication.class, args);
    }

}
