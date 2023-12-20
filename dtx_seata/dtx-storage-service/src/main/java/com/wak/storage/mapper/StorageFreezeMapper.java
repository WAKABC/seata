package com.wak.storage.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.commons.entities.StorageFreeze;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/20 13:16
 * @Description TODO
 * @Version 1.0
 */
public interface StorageFreezeMapper extends Mapper<StorageFreeze> {
    /**
     * 删除根据txid
     *
     * @param txId tx id
     * @return int
     */
    int deleteByTxId(@Param("txId")String txId);

    /**
     * 找到一个根据txid
     *
     * @param txId tx id
     * @return {@code StorageFreeze}
     */
    StorageFreeze findOneByTxId(@Param("txId")String txId);

    /**
     * 解除冻结根据txid
     *
     * @param txId tx id
     * @return int
     */
    int unFreezeByTxId(@Param("txId")String txId);
}