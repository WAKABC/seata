package com.wak.storage.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wak.commons.entities.StorageFreeze;
import com.wak.storage.mapper.StorageFreezeMapper;
import com.wak.storage.mapper.StorageMapper;
import com.wak.storage.service.StorageTccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author wuankang
 * @date 2023/12/20 13:00
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class StorageTccServiceImpl implements StorageTccService {
    @Resource
    private StorageMapper storageMapper;

    @Resource
    private StorageFreezeMapper storageFreezeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrease(Long productId, Integer count) {
        String xid = RootContext.getXID();
        //业务悬挂
        //模拟异常：sleep 60s 以上回滚后会继续执行后续方法出现业务悬挂
        StorageFreeze freeze = storageFreezeMapper.findOneByTxId(xid);
        if (ObjUtil.isNotEmpty(freeze)) {
            return;
        }
        //判断库存是否充足
        Integer totalCount = storageMapper.findOneTotalByProductId(productId);
        if (count > totalCount) {
            throw new RuntimeException("库存不足,当前库存:" + totalCount);
        }
        //减少总库存
        int decResult = storageMapper.decreaseTotalByProductId(count, productId);
        if (decResult <= 0) {
            log.info("扣减库存失败, productId:{}, count:{}", productId, count);
            return;
        }
        //增加冻结库存
        StorageFreeze storageFreeze = new StorageFreeze();
        storageFreeze.setTxId(xid);
        storageFreeze.setTxState(StorageFreeze.TccStatusEnum.TCC_TRY_CODE);
        storageFreeze.setProductId(productId);
        storageFreeze.setCount(count);
        storageFreezeMapper.insertSelective(storageFreeze);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean commit(BusinessActionContext context) {
        //删除临时表数据，如果不存在中间表，就修改事务状态字段
        String xid = context.getXid();
        int deleteResult = storageFreezeMapper.deleteByTxId(xid);
        return deleteResult == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rollback(BusinessActionContext context) {
        //空回滚
        //模拟空回滚方案：此RM，Try时创造异常，即可空回滚
        String xid = context.getXid();
        StorageFreeze freeze = storageFreezeMapper.findOneByTxId(xid);
        if (ObjUtil.isEmpty(freeze)) {
            Long productId = context.getActionContext("productId", Long.class);
            StorageFreeze storageFreeze = new StorageFreeze();
            storageFreeze.setTxId(xid);
            storageFreeze.setCount(0);
            storageFreeze.setTxState(StorageFreeze.TccStatusEnum.TCC_CANCEL_CODE);
            storageFreeze.setProductId(productId);
            storageFreezeMapper.insertSelective(storageFreeze);
            return true;
        }
        //幂等
        if (freeze.getTxState() == StorageFreeze.TccStatusEnum.TCC_CANCEL_CODE) {
            return true;
        }
        //库存回退
        int incResult = storageMapper.incrementTotalByProductId(freeze.getCount(), freeze.getProductId());
        if (incResult < 0) {
            throw new RuntimeException("库存回退失败，xid:" + xid + ", productId:" + freeze.getProductId() + ", count:" + freeze.getCount());
        }
        //如果使用返回false方案，库存回退成功了，冻结库存失败了，会不会重试导致执行多次库存回退（结果：会）
        // 模拟方案：其他RM出错回滚，此RM正常执行到这，报错重试，会不会库存增加
        // 模拟结果，不能返回false，必须抛出异常才会回滚，否则库存会一直增加
        //冻结清零
        int unfreezeResult = storageFreezeMapper.unFreezeByTxId(xid);
        if (unfreezeResult <= 0) {
            throw new RuntimeException("冻结库存清零失败，xid:" + xid);
        }
        return true;
    }
}
