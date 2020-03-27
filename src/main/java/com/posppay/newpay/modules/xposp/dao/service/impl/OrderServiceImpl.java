package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.mapper.OrderDao;
import com.posppay.newpay.modules.xposp.dao.service.OrderService;
import com.posppay.newpay.modules.xposp.entity.Order;
import com.posppay.newpay.modules.xposp.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 系统订单表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    /**
     * 新增订单流水
     *
     * @param order
     * @return
     */
    @Override
    public Order insertOne(Order order) {
        Assert.notNull(order, "order should not be null!");
        Assert.notNull(order.getOrdAmt(), "ordAmt should not be null!");
        Assert.notNull(order.getMrchNo(), "mrchNo should not be null!");
        Assert.notNull(order.getOrgNo(), "orgNo should not be null!");
        String ordId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        order.setOrdId(ordId);
        order.setCreTime(new Date());
        try {
            baseMapper.insert(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Order updateOrder(Order order) {
        EntityWrapper<Order> entityWrapper = new EntityWrapper<Order>();
        Date creTime = order.getCreTime();
        entityWrapper.eq("ORD_ID",order.getOrdId());
        //时间从0点到24点
        if (null != creTime) {
            entityWrapper.ge("cre_time", DateUtils.getInstall().nowDate(creTime));
            entityWrapper.lt("cre_time",DateUtils.getInstall().nextDate(creTime));
        }
        baseMapper.update(order,entityWrapper);
        return order;
    }

    /**
     * 根据原始订单号和机构号和交易日期来确定唯一一条订单流失
     *
     * @param ordNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public Order querySingleOrder(String ordNo, String orgNo, String transDate) {
        Assert.notNull(ordNo, "orderNo should not be null!");
        Assert.notNull(orgNo, "orgNo should not be null!");
        EntityWrapper<Order> entityWrapper = new EntityWrapper<Order>();
        entityWrapper.eq("ORD_NO", ordNo);
        entityWrapper.eq("ORG_NO", orgNo);
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        //时间从0点到24点
        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date), "yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)), "yyyy-MM-dd HH:mm:ss"));
        }
        Order orderResult = null;
        List<Order> orders = baseMapper.selectList(entityWrapper);
        if (null != orders && 0 < orders.size()) {
            for (Order order : orders) {
                if (null == orderResult) {
                    orderResult = order;
                }
            }
        }
        return orderResult;
    }
}
