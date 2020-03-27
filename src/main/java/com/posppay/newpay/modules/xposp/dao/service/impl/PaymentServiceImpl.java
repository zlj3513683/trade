package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.PaymentDao;
import com.posppay.newpay.modules.xposp.dao.service.PaymentService;
import com.posppay.newpay.modules.xposp.entity.Payment;
import com.posppay.newpay.modules.xposp.utils.DateUtils;
import com.posppay.newpay.modules.xposp.utils.StringUtils;
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
 * 平台流水表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentDao, Payment> implements PaymentService {
    @Override
    public Payment addPayment(Payment payment) {
        Assert.notNull(payment, "payment should not be null!");
        Assert.notNull(payment.getTransAmount(), "transAmount should not be null!");
        Assert.notNull(payment.getMrchNo(), "mrchNo should not be null!");
        Assert.notNull(payment.getOutOrderNo(), "outOrderNo should not be null!");
        Assert.notNull(payment.getPayNo(), "payNo should not be null!");
        String paymentId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        payment.setPayId(paymentId);
        try{

            baseMapper.insert(payment);
        }catch (Exception e){
            e.printStackTrace();
        }
        return payment;
    }

    /**
     * 根据下游商户号和机构号来获取唯一一条平台流水
     *
     * @param outOrderNo
     * @param orgNo
     * @return
     */
    @Override
    public Payment findByOutOrderNo(String outOrderNo, String orgNo, String transDate) {
        Assert.notNull(outOrderNo, "outOrderNo should not be null!");
        Assert.notNull(orgNo, "outOrderNo should not be null!");
        EntityWrapper<Payment> entityWrapper = new EntityWrapper<Payment>();
        entityWrapper.eq("OUT_ORDER_NO", outOrderNo);
        entityWrapper.eq("ORG_NO", orgNo);
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        //时间从0点到24点
        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date),"yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)),"yyyy-MM-dd HH:mm:ss"));
        }
        Payment paymentResult = null;
        List<Payment> payments = baseMapper.selectList(entityWrapper);
        if (null != payments && 0 < payments.size()) {
            for (Payment payment : payments) {
                if (null == paymentResult) {
                    paymentResult = payment;
                }
            }
        }
        return paymentResult;
    }

    /**
     * 根据下和机构号来获取唯一一条平台流水
     *
     * @param payNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public Payment findByPayNo(String payNo, String orgNo, String transDate) {
        Assert.notNull(payNo, "payNo should not be null!");
        Assert.notNull(orgNo, "outOrderNo should not be null!");
        EntityWrapper<Payment> entityWrapper = new EntityWrapper<Payment>();
        entityWrapper.eq("PAY_NO", payNo);
        entityWrapper.eq("ORG_NO", orgNo);
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        //时间从0点到24点
        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date),"yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)),"yyyy-MM-dd HH:mm:ss"));
        }
        Payment paymentResult = null;
        List<Payment> payments = baseMapper.selectList(entityWrapper);
        if (null != payments && 0 < payments.size()) {
            for (Payment payment : payments) {
                if (null == paymentResult) {
                    paymentResult = payment;
                }
            }
        }
        return paymentResult;
    }

    /**
     * 根据下游商户号和机构号来获取唯一一条平台流水
     *
     * @param outTransactionId
     * @param orgNo
     * @param transDate
     * @param transType
     * @return
     */
    @Override
    public Payment findByOutTransctionId(String outTransactionId, String orgNo, String transDate, String transType) {
        Assert.notNull(outTransactionId, "outOrderNo should not be null!");
        Assert.notNull(orgNo, "outOrderNo should not be null!");

        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        EntityWrapper<Payment> entityWrapper = new EntityWrapper<Payment>();
        entityWrapper.eq("OUT_TRANSACTION_ID", outTransactionId);
        entityWrapper.eq("ORG_NO", orgNo);
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        //时间从0点到24点
        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date),"yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)),"yyyy-MM-dd HH:mm:ss"));
        }

//        if (null!=transTypes&&0<transTypes.size()){
//            entityWrapper.in("TRANS_TYPE",transTypes);
//        }
        if (StringUtils.isNotBlank(transType)){
            entityWrapper.eq("TRANS_TYPE",transType);
        }
//        List<Payment> payments = baseMapper.selectList(entityWrapper);
        Payment paymentResult = null;
        List<Payment> payments = baseMapper.selectList(entityWrapper);
        if (null != payments && 0 < payments.size()) {
            for (Payment payment : payments) {
                if (null == paymentResult) {
                    paymentResult = payment;
                }
            }
        }
        return paymentResult;
//        return payments;
    }

    /**
     * 更新平台流水
     *
     * @param payment
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Payment updatePayment(Payment payment) {
        EntityWrapper<Payment> entityWrapper = new EntityWrapper<Payment>();
        Date creTime = payment.getCreTime();
        entityWrapper.eq("PAY_NO",payment.getPayNo());
        //时间从0点到24点
        if (null != creTime) {
            entityWrapper.ge("cre_time", DateUtils.getInstall().nowDate(creTime));
            entityWrapper.lt("cre_time",DateUtils.getInstall().nextDate(creTime));
        }
        baseMapper.update(payment,entityWrapper);
        return payment;
    }
}
