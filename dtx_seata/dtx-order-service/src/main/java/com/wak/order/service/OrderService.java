package com.wak.order.service;

import com.wak.commons.apis.AccountFeignApi;
import com.wak.commons.apis.StorageFeignApi;
import com.wak.commons.entities.Order;
import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.wak.order.mapper.OrderMapper;

/**
 * @author wuankang
 * @date 2023/12/19 9:41
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AccountFeignApi accountFeignApi;

    @Resource
    private StorageFeignApi storageFeignApi;

    /**
     * 创建
     *
     * @param order 订单
     * @return {@code Order}
     * @GlobalTransactional + @Transactional = SEATA.XA/AT
     */
    /*@GlobalTransactional + @Transactional(rollbackFor = Exception.class) XA*/
    //@GlobalTransactional(name = "seata-order-server", rollbackFor = Exception.class) AT
    @GlobalTransactional(name = "order-server", rollbackFor = Exception.class)
    public Order create(Order order) {
        //全局事务id
        String xid = RootContext.getXID();
        //新建订单
        order.setStatus(0);
        int count = orderMapper.insertSelective(order);
        if (count <= 0) throw new RuntimeException("新建订单失败...");
        log.info("******订单创建完成****** \n order:{}", order);
        //扣库存
        storageFeignApi.decreaseTcc(order.getProductId(), order.getCount());
        log.info("******扣减库存成功*******");
        //扣金额
        accountFeignApi.decreaseTcc(order.getUserId(), order.getMoney());
        log.info("******扣减金额成功*******");
        //修改订单状态
        int updateResult = orderMapper.updateStatusById(1, order.getId());
        if (updateResult > 0) log.info("******更新订单状态成功*******");
        log.info("********订单创建完成**********\n xid:{}", xid);
        return order;
    }

}
