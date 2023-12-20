package com.wak.order.controller;

import com.wak.commons.entities.Order;
import com.wak.commons.resp.ApiResponse;
import com.wak.order.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * (dtx_order)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;

    @PostMapping("/add")
    public ApiResponse<Order> addOrder(@RequestBody Order order){
        Order result = orderService.create(order);
        return ApiResponse.success(result);
    }

}
