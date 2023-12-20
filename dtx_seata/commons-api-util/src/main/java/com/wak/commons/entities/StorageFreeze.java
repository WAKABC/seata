package com.wak.commons.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/20 13:16
 * @Description TODO
 * @Version 1.0
 */
/**
    * 库存冻结表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dtx_storage_freeze")
public class StorageFreeze {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 事务编号
     */
    @Column(name = "tx_id")
    private String txId;

    /**
     * 产品编号
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 冻结数量
     */
    @Column(name = "`count`")
    private Integer count;

    /**
     * 事务状态 try:0, confirm:1, cancel: 2
     */
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