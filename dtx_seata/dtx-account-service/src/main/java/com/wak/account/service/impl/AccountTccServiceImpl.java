package com.wak.account.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wak.account.mapper.AccountFreezeMapper;
import com.wak.account.mapper.AccountMapper;
import com.wak.account.service.AccountTccService;
import com.wak.commons.entities.AccountFreeze;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/12/19 16:52
 * @Description TODO seata tcc模式
 * @Version 1.0
 */
@Service
@Slf4j
public class AccountTccServiceImpl implements AccountTccService {
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountFreezeMapper accountFreezeMapper;

    /**
     * 减少金额
     *
     * @param userId 用户id
     * @param amount 金额
     * @return {@code String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrease(Long userId, BigDecimal amount) {
        String xid = RootContext.getXID();
        //业务悬挂判断？大家回滚你去try
        AccountFreeze freeze = accountFreezeMapper.findOneByTxId(xid);
        if (ObjUtil.isNotEmpty(freeze)) {
            return;
        }
        //判断金额是否充足
        BigDecimal totalMoney = accountMapper.findTotalMoneyByUserId(userId);
        if (amount.compareTo(totalMoney) > 0) {
            throw new RuntimeException("用户账户余额不足...");
        }
        //扣减用户金额
        accountMapper.decreaseTotalMoneyByUserId(amount, userId);
        //创建金额冻结对象
        AccountFreeze accountFreeze = new AccountFreeze();
        accountFreeze.setTxId(xid);
        accountFreeze.setUserId(userId);
        accountFreeze.setFreezeMoney(amount);
        accountFreeze.setTxState(AccountFreeze.TccStatusEnum.TCC_TRY_CODE);
        accountFreezeMapper.insertSelective(accountFreeze);
    }

    /**
     * 提交
     *
     * @param context 上下文
     * @return {@code Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean commit(BusinessActionContext context) {
        //提交删除冻结记录，删除操作天生幂等
        String xid = context.getXid();
        int count = accountFreezeMapper.deleteByTxId(xid);
        return count == 1;
    }

    /**
     * 回滚
     *
     * @param context 上下文
     * @return {@code Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rollback(BusinessActionContext context) {
        //空回滚处理
        String xid = context.getXid();
        AccountFreeze accountFreeze = accountFreezeMapper.findOneByTxId(xid);
        //空回滚
        if (ObjUtil.isEmpty(accountFreeze)) {
            Long userId = context.getActionContext("userId", Long.class);
            accountFreeze = new AccountFreeze();
            accountFreeze.setUserId(userId);
            accountFreeze.setTxId(xid);
            accountFreeze.setTxState(AccountFreeze.TccStatusEnum.TCC_CANCEL_CODE);
            accountFreeze.setFreezeMoney(BigDecimal.valueOf(0));
            accountFreezeMapper.insertSelective(accountFreeze);
            return true;
        }
        //幂等处理
        Byte state = accountFreezeMapper.findOneTxStateByTxId(xid);
        if (state == AccountFreeze.TccStatusEnum.TCC_CANCEL_CODE) {
            return true;
        }
        //金额回退
        //帐户金额增加
        int incResult = accountMapper.incrementTotalMoneyByUserId(accountFreeze.getFreezeMoney(), accountFreeze.getUserId());
        if (incResult <= 0) {
            throw new RuntimeException("金额退还失败，xid:" + xid + ", userId:" + accountFreeze.getUserId());
        }
        //冻结金额清零
        int unFreezeResult = accountFreezeMapper.unFreezeMoneyByTxId(xid);
        if (unFreezeResult <= 0) {
            throw new RuntimeException("金额解冻失败，xid:" + xid);
        }
        return true;
    }
}
