package com.wak.commons.entities;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/19 11:15
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dtx_account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 帐户总金额，单位元
     */
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    /**
     * 冻结金额，单位元
     */
    @Column(name = "freeze_money")
    private BigDecimal freezeMoney;

    /**
     * 状态，0：正常；1：冻结
     */
    @Column(name = "`status`")
    private Integer status;
}