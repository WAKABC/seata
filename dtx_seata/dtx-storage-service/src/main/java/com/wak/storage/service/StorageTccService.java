package com.wak.storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author wuankang
 * @date 2023/12/20 12:57
 * @Description TODO seata Tcc 模式
 * @Version 1.0
 */
@LocalTCC
public interface StorageTccService {
    @TwoPhaseBusinessAction(name = "storageDecreaseTcc", commitMethod = "commit", rollbackMethod = "rollback")
    void decrease(@BusinessActionContextParameter("productId") Long productId, @BusinessActionContextParameter("count") Integer count);

    Boolean commit(BusinessActionContext context);

    Boolean rollback(BusinessActionContext context);
}
