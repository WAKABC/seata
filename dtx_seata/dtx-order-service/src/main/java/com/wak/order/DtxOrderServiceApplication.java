package com.wak.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/19
 * @description TODO
 */
@SpringBootApplication(scanBasePackages = {"com.wak.order", "com.wak.commons"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wak.commons.apis"})
@MapperScan(basePackages = {"com.wak.order.mapper"})
public class DtxOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtxOrderServiceApplication.class, args);
    }

}
