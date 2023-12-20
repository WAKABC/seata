package com.wak.storage.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.wak.storage.mapper.StorageMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuankang
 * @date 2023/12/19 11:36
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class StorageService {

    @Resource
    private StorageMapper storageMapper;

    @Transactional(rollbackFor = Exception.class)
    public void decrease(Long productId, int count) {
        storageMapper.decreaseTotalByProductId(count, productId);
    }
}
