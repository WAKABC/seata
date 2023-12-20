package com.wak.commons.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/19 11:36
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dtx_storage")
public class Storage {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 总库存
     */
    @Column(name = "total")
    private Integer total;

    /**
     * 锁定的库存
     */
    @Column(name = "`lock`")
    private Integer lock;
}