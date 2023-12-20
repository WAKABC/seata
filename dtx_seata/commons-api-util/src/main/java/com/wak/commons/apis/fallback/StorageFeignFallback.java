package com.wak.commons.apis.fallback;

import com.wak.commons.apis.StorageFeignApi;
import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/12/19 10:41
 * @Description TODO
 * @Version 1.0
 */
@Component
public class StorageFeignFallback implements StorageFeignApi {
    @Override
    public ApiResponse<String> decrease(Long productId, int count) {
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                "openfeign call failed, decrease productId:" + productId + ", count:" + count);
    }

    @Override
    public ApiResponse<String> decreaseTcc(Long productId, int count) {
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                "openfeign call failed, decreaseTcc productId:" + productId + ", count:" + count);
    }
}
