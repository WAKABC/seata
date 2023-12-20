package com.wak.storage.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.commons.entities.Storage;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/19 11:36
 * @Description TODO
 * @Version 1.0
 */
public interface StorageMapper extends Mapper<Storage> {

    /**
     * 查询库存总数根据产品id
     *
     * @param productId 产品id
     * @return {@code Integer}
     */
    Integer findOneTotalByProductId(@Param("productId")Long productId);

    /**
     * 减少库存根据产品id
     *
     * @param count     数量
     * @param productId 产品id
     * @return int
     */
    int decreaseTotalByProductId(@Param("count")Integer count, @Param("productId")Long productId);

    /**
     * 增加库存根据产品id
     *
     * @param count     数
     * @param productId 产品id
     * @return int
     */
    int incrementTotalByProductId(@Param("count")Integer count, @Param("productId")Long productId);
}