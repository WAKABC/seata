package com.wak.account.controller;

import com.wak.account.service.AccountTccService;
import com.wak.account.service.impl.AccountService;
import com.wak.commons.resp.ApiResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * (dtx_account)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    /**
     * 服务对象
     */
    @Resource
    private AccountService accountService;

    @Resource(name = "accountTccServiceImpl")
    private AccountTccService accountTccService;

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
}
