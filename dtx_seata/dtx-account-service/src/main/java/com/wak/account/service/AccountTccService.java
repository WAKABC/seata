package com.wak.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 17:03
 * @Description TODO seata tcc 模式
 * @Version 1.0
 */
@LocalTCC
public interface AccountTccService {
    /**
     * 扣减金额
     *
     * @param userId 用户id
     * @param money  钱
     * @return {@code String}
     */
    @TwoPhaseBusinessAction(name = "accountDecreaseTcc", commitMethod = "commit", rollbackMethod = "rollback")
    void decrease(@BusinessActionContextParameter("userId") Long userId, @BusinessActionContextParameter("money") BigDecimal money);

    /**
     * 执行TCC事务时，成功就提交事务
     *
     * @param context 上下文
     * @return {@code Boolean}
     */
    Boolean commit(BusinessActionContext context);

    /**
     * 执行TCC事务时，失败就回滚事务
     *
     * @param context 上下文
     * @return {@code Boolean}
     */
    Boolean rollback(BusinessActionContext context);
}
