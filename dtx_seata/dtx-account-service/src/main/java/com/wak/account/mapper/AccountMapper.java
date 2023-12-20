package com.wak.account.mapper;

import com.wak.commons.entities.Account;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 11:15
 * @Description TODO
 * @Version 1.0
 */
public interface AccountMapper extends Mapper<Account> {
    /**
     * 根据用户id扣减帐户金额
     *
     * @param amount  金额
     * @param userId 用户id
     * @return int
     */
    int decreaseTotalMoneyByUserId(@Param("amount")BigDecimal amount, @Param("userId")Long userId);

    /**
     * 根据用户id退还金额
     *
     * @param amount 金额
     * @param userId
     * @return int
     */
    int incrementTotalMoneyByUserId(@Param("amount")BigDecimal amount, @Param("userId")Long userId);

    /**
     * 找到总计。钱根据用户id
     *
     * @param userId 用户id
     * @return {@code List<BigDecimal>}
     */
    BigDecimal findTotalMoneyByUserId(@Param("userId")Long userId);
}