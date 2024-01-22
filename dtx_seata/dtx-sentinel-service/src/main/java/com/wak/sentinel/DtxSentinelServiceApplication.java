package com.wak.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2024/01/18
 * @description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DtxSentinelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtxSentinelServiceApplication.class, args);
    }

}
