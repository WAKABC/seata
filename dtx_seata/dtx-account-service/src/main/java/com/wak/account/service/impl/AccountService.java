package com.wak.account.service.impl;

import com.wak.account.mapper.AccountMapper;
import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 11:15
 * @Description TODO seata XA / AT 模式
 * @Version 1.0
 */
@Service
@Slf4j
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 减少金额
     *
     * @param userId 用户id
     * @param amount 金额
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> decrease(Long userId, BigDecimal amount) {
        int count = accountMapper.decreaseTotalMoneyByUserId(amount, userId);
        if (count > 0) {
            log.info("用户金额扣减成功, userId:{}, amount:{}", userId, amount);
            return ApiResponse.success(null);
        }
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), "fail");
    }
}
