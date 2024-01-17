package com.wak.order.controller;

import cn.hutool.core.util.IdUtil;
import com.wak.commons.resp.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author wuankang
 * @date 2024/1/11 16:31
 * @Description TODO 断路器:测试类
 * @Version 1.0
 */
@RestController
@Slf4j
public class CircuitBreakerController {

    /**
     * 断路器:服务熔断测试接口
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/circuit/order/{id}")
    public ApiResponse<String> circuitOrder(@PathVariable("id") Integer id) {
        //基于计数的滑动窗口调用
        if (id < 0) {
            throw new IllegalArgumentException("id 不能负数");
        }
        //基于时间的滑动窗口调用
        if (id == 9999) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ApiResponse.success("Hello, circuit! inputId:  " + id + " \t " + IdUtil.fastSimpleUUID());
    }

    /**
     * 断路器：隔离测试接口
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/bulkhead/order/{id}")
    public ApiResponse<String> bulkHeadTest(@PathVariable("id") Integer id) {
        if (id == -4) throw new RuntimeException("----bulkhead id 不能-4");
        if (id == 9999) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ApiResponse.success("Hello, bulkhead! inputId:  " + id + " \t " + IdUtil.simpleUUID());
    }

    /**
     * 断路器：限流测试接口
     *
     * @param id id
     * @return {@code String}
     */
    @GetMapping(value = "/ratelimiter/{id}")
    public ApiResponse<String> rateLimiter(@PathVariable("id") Integer id) {
        return ApiResponse.success("Hello, rateLimiter欢迎到来 inputId:  " + id + " \t " + IdUtil.simpleUUID());
    }
}
