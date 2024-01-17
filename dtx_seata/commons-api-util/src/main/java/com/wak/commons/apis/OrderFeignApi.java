package com.wak.commons.apis;

import com.wak.commons.resp.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wuankang
 * @date 2024/1/11 18:33
 * @Description TODO order 断路器openfeign接口
 * @Version 1.0
 */
@FeignClient(name = "dtx-order")
public interface OrderFeignApi {
    /**
     * 熔断测试接口
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/circuit/order/{id}")
    ApiResponse<String> circuitOrder(@PathVariable("id") Integer id);

    /**
     * 隔離测试接口
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/bulkhead/order/{id}")
    public ApiResponse<String> bulkHeadTest(@PathVariable("id") Integer id);

    /**
     * 限流测试接口
     *
     * @param id id
     * @return {@code String}
     */
    @GetMapping(value = "/ratelimiter/{id}")
    ApiResponse<String> rateLimiter(@PathVariable("id") Integer id);
}
