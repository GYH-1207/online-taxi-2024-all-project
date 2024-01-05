package com.xiaoxi.serviceOrder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoxi.serviceOrder.mapper")
public class ServiceOrderApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ServiceOrderApplication.class);
    }
}
