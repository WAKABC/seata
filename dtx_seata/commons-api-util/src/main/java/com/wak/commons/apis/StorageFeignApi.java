package com.wak.commons.apis;

import com.wak.commons.apis.fallback.StorageFeignFallback;
import com.wak.commons.resp.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuankang
 * @date 2023/12/19 10:40
 * @Description TODO 库存 openfeign client
 * @Version 1.0
 */
@FeignClient(value = "dtx-storage", fallback = StorageFeignFallback.class)
public interface StorageFeignApi {
    /**
     * 减少库存 AT
     *
     * @param productId 产品id
     * @param count     数量
     * @return {@code String}
     */
    @GetMapping("/storage/at/decrease")
    ApiResponse<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") int count);

    /**
     * 减少库存 TCC
     *
     * @param productId 产品id
     * @param count     数
     * @return {@code ApiResponse<String>}
     */
    @GetMapping("/storage/tcc/decrease")
    ApiResponse<String> decreaseTcc(@RequestParam("productId") Long productId, @RequestParam("count") int count);
}