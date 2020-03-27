package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.Payment;

import java.util.List;

/**
 * <p>
 * 平台流水表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
public interface PaymentService extends IService<Payment> {
    /**
     * 新增平台流水
     *
     * @param payment
     * @return
     */
    Payment addPayment(Payment payment);

    /**
     * 根据下游商户号和机构号来获取唯一一条平台流水
     *
     * @param outOrderNO
     * @param orgNo
     * @return
     */
    Payment findByOutOrderNo(String outOrderNO, String orgNo, String transDate);
    /**
     * 根据下游商户号和机构号来获取唯一一条平台流水
     *
     * @param outOrderNO
     * @param orgNo
     * @return
     */
    Payment findByPayNo(String outOrderNO, String orgNo, String transDate);

    /**
     * 根据下游商户号和机构号来获取唯一一条平台流水
     *
     * @param outTransactionId
     * @param orgNo
     * @return
     */
    Payment findByOutTransctionId(String outTransactionId, String orgNo, String transDate, String transType);

    /**
     * 更新平台流水
     *
     * @param payment
     * @return
     */
    Payment updatePayment(Payment payment);

}
