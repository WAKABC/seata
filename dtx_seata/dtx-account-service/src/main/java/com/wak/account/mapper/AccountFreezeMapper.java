package com.wak.account.mapper;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

import com.wak.commons.entities.AccountFreeze;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/19 18:24
 * @Description TODO
 * @Version 1.0
 */
public interface AccountFreezeMapper extends Mapper<AccountFreeze> {
    /**
     * 更新tx状态根据txid
     *
     * @param txState 更新tx状态
     * @param txid           txid
     * @return int
     */
    int updateTxStateByTxId(@Param("txState")Byte txState, @Param("txid")String txid);

    /**
     * 删除根据txid
     *
     * @param txid txid
     * @return int
     */
    int deleteByTxId(@Param("txid")String txid);

    /**
     * 找到一个根据txid
     *
     * @param txid txid
     * @return {@code AccountFreeze}
     */
    AccountFreeze findOneByTxId(@Param("txid")String txid);

    /**
     * 找到一个tx状态根据txid
     *
     * @param txid txid
     * @return {@code Byte}
     */
    Byte findOneTxStateByTxId(@Param("txid")String txid);

    /**
     * 根据txid解冻金额并设置事务状态为cancel
     *
     * @param txid
     * @return int
     */
    int unFreezeMoneyByTxId(@Param("txid")String txid);
}