package com.wak.sentinel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuankang
 * @date 2024/1/21 10:56
 * @Description TODO 授权测试接口
 * @Version 1.0
 */
@RestController
@Slf4j
public class AuthorizeController {
    @GetMapping(value = "/authorize")
    public String requestAuthorize()
    {
        log.info("测试Sentinel授权规则requestAuthorize,success访问成功");
        return "Sentinel授权规则,含黑白名单配置";
    }
}
