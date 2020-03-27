package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.Order;

/**
 * <p>
 * 系统订单表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
public interface OrderService extends IService<Order> {

    /**
     * 新增订单流水
     * @param order
     * @return
     */
    Order insertOne(Order order);

    /**
     * 根据原始订单号和机构号和交易日期来确定唯一一条订单流失
     * @param ordNo
     * @param orgNo
     * @param transDate
     * @return
     */
    Order querySingleOrder(String ordNo,String orgNo,String transDate);

    Order updateOrder(Order order);

}
