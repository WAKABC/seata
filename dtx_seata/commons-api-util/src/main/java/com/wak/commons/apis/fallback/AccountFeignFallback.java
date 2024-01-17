package com.wak.commons.apis.fallback;

import com.wak.commons.apis.AccountFeignApi;
import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 10:34
 * @Description TODO
 * @Version 1.0
 */
@Component
public class AccountFeignFallback implements AccountFeignApi {
    @Override
    public ApiResponse<String> decrease(Long userId, BigDecimal amount) {
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                "openfeign call failed, decrease userId:" + userId + ", amount:" + amount);
    }

    @Override
    public ApiResponse<String> decreaseTcc(Long userId, BigDecimal amount) {
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                "openfeign call failed, decreaseTcc userId:" + userId + ", amount:" + amount);
    }
}
