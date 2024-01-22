package com.wak.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuankang
 * @date 2024/1/18 17:20
 * @Description TODO sentinel 热点测试接口
 * @Version 1.0
 */
@RestController
public class FlowLimitController {

    /**
     * 限流测试接口
     *
     * @return {@code String}
     */
    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }

    /**
     * 热点测试
     *
     * @param p1 p1
     * @param p2 p2
     * @return {@code String}
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String dealHandler_testHotKey(String p1, String p2, BlockException exception) {
        return "-----dealHandler_testHotKey";
    }

}
