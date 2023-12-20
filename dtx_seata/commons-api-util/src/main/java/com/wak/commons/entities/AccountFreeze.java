package com.wak.commons.entities;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/19 18:24
 * @Description TODO
 * @Version 1.0
 */
/**
    * 帐户冻结表，tcc 事务用
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dtx_account_freeze")
public class AccountFreeze {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "tx_id")
    private String txId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "freeze_money")
    private BigDecimal freezeMoney;

    @Column(name = "tx_state")
    private Byte txState;

    /**
     * @author wuankang
     * @version 1.0.0
     * @date 2023/12/19
     * @description TODO TCC事务状态
     */
    public static abstract class TccStatusEnum{
        /**
         * TCC尝试码
         */
        public static final byte TCC_TRY_CODE = 0;
        /**
         * TCC确认码
         */
        public static final byte TCC_CONFIRM_CODE = 1;
        /**
         * TCC取消码
         */
        public static final byte TCC_CANCEL_CODE = 2;
    }
}