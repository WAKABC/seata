package com.wak.account.controller;

import com.wak.account.service.AccountTccService;
import com.wak.account.service.impl.AccountService;
import com.wak.commons.apis.OrderFeignApi;
import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * (dtx_account)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    /**
     * 服务对象
     */
    @Resource
    private AccountService accountService;

    @Resource(name = "accountTccServiceImpl")
    private AccountTccService accountTccService;

    @Resource
    private OrderFeignApi orderFeignApi;

    /**
     * 扣减帐户 AT/XA模式接口
     *
     * @param userId 用户id
     * @param amount 金额
     * @return {@code String}
     */
    @GetMapping("/at/decrease")
    public ApiResponse<String> decrease(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount) {
        accountService.decrease(userId, amount);
        return ApiResponse.success(null);
    }

    @GetMapping("/tcc/decrease")
    public ApiResponse<String> decreaseTcc(@RequestParam("userId") Long userId, @RequestParam("amount") BigDecimal amount) {
        accountTccService.decrease(userId, amount);
        return ApiResponse.success(null);
    }


    /**
     * resilience4j断路器测试接口
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/circuit/{id}")
    @CircuitBreaker(name = "dtx-order", fallbackMethod = "circuitBreakerFallback")
    public ApiResponse<String> circuitBreaker(@PathVariable("id") Integer id) {
        return orderFeignApi.circuitOrder(id);
    }

    /**
     * 断路器服务降级
     *
     * @param id        id
     * @param throwable throwable
     * @return {@code String}
     */
    public ApiResponse<String> circuitBreakerFallback(Integer id, Throwable throwable) {
        log.info("myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
        return ApiResponse.fail(ResultCodeEnum.FORBIDDEN.getCode(), "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
    }

    /**
     * resilience4j bulkhead SEMAPHORE测试方法
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/bulkhead/{id}")
    @Bulkhead(name = "dtx-order", fallbackMethod = "bulkHeadFallback", type = Bulkhead.Type.SEMAPHORE)
    public ApiResponse<String> bulkHead(@PathVariable("id") Integer id) {
        return orderFeignApi.bulkHeadTest(id);
    }

    /**
     * SEMAPHORE隔离降级
     *
     * @param t t
     * @return {@code ApiResponse<String>}
     */
    public ApiResponse<String> bulkHeadFallback(Throwable t) {
        return ApiResponse.success("myBulkheadFallback，隔板超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
    }


    /**
     * resilience4j bulkhead THREADPOOL测试方法
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/bulkhead/pool/{id}")
    @Bulkhead(name = "dtx-order", fallbackMethod = "bulkHeadPoolFallback", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<ApiResponse> bulkHeadThreadPool(@PathVariable("id") Integer id) {
        CompletableFuture<ApiResponse> future = CompletableFuture.supplyAsync(() -> orderFeignApi.bulkHeadTest(id));
        return future;
    }

    /**
     * THREADPOOL隔离降级
     *
     * @param t t
     * @return {@code ApiResponse<String>}
     */
    public CompletableFuture<ApiResponse> bulkHeadPoolFallback(Throwable t) {
        return CompletableFuture.supplyAsync(() -> ApiResponse.fail(ResultCodeEnum.FORBIDDEN.getCode(), "Bulkhead.Type.THREADPOOL，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~"));
    }

    /**
     * 限流
     *
     * @param id id
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/ratelimiter/{id}")
    @RateLimiter(name = "dtx-order", fallbackMethod = "rateLimiterFallback")
    public ApiResponse<String> rateLimiter(@PathVariable("id") Integer id) {
        return orderFeignApi.rateLimiter(id);
    }

    /**
     * 限流降级
     *
     * @param id id
     * @param t  t
     * @return {@code ApiResponse<String>}
     */
    public ApiResponse<String> rateLimiterFallback(Integer id, Throwable t) {
        return ApiResponse.fail(ResultCodeEnum.FORBIDDEN.getCode(), "RateLimiter,你被限流了，禁止访问/(ㄒoㄒ)/~~");
    }
}
