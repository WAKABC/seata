package com.wak.storage.controller;

import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import com.wak.storage.service.StorageTccService;
import com.wak.storage.service.impl.StorageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * (dtx_storage)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    /**
     * 服务对象
     */
    @Resource
    private StorageService storageService;

    @Resource(name = "storageTccServiceImpl")
    private StorageTccService storageTccService;

    @GetMapping("/at/decrease")
    public ApiResponse<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") int count) {
        storageService.decrease(productId, count);
        return ApiResponse.success("库存扣减成功");
    }

    @GetMapping("/tcc/decrease")
    public ApiResponse<String> decreaseTcc(@RequestParam("productId") Long productId, @RequestParam("count") int count) {
        storageTccService.decrease(productId, count);
        return ApiResponse.success("库存扣减成功");
    }
}
