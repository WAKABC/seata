package com.wak.commons.entities;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/19 9:41
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dtx_order")
public class Order {
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
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 数量
     */
    @Column(name = "`count`")
    private Integer count;

    /**
     * 订单金额，单位分
     */
    @Column(name = "money")
    private BigDecimal money;

    /**
     * 订单状态，0：创建中；1：已完结
     */
    @Column(name = "`status`")
    private Integer status;
}