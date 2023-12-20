package com.wak.order.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.commons.entities.Order;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/19 9:41
 * @Description TODO
 * @Version 1.0
 */
public interface OrderMapper extends Mapper<Order> {
    /**
     * 更新状态根据id
     *
     * @param updatedStatus 更新状态
     * @param id            id
     * @return int
     */
    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Long id);
}