package com.wak.commons.apis;

import com.wak.commons.apis.fallback.AccountFeignFallbackApi;
import com.wak.commons.resp.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 10:33
 * @Description TODO
 * @Version 1.0
 */
@FeignClient(value = "dtx-account", fallback = AccountFeignFallbackApi.class)
public interface AccountFeignApi {
    /**
     * 扣减金额 AT模式
     *
     * @param userId 帐户id
     * @param amount    金额
     * @return {@code String}
     */
    @GetMapping("/account/at/decrease")
    ApiResponse<String> decrease(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount);

    /**
     * 扣减金额 TCC模式
     *
     * @param userId 用户id
     * @param amount 金额
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/account/tcc/decrease")
    ApiResponse<String> decreaseTcc(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount);
}
