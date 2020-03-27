package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.mapper.QrChnFlowGtDao;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowGtService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowGt;
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
 * 星pos扫码渠道流水表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-07-18
 */
@Service
public class QrChnFlowGtServiceImpl extends ServiceImpl<QrChnFlowGtDao, QrChnFlowGt> implements QrChnFlowGtService {

    /**
     * 新增渠道流水
     *
     * @param qrChnFlowGt
     * @return
     */
    @Override
    public QrChnFlowGt inserQrFow(QrChnFlowGt qrChnFlowGt) {
        String qrChnFlowGtId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        qrChnFlowGt.setQrChnFlowGtId(qrChnFlowGtId);
        qrChnFlowGt.setCreTime(new Date());
        baseMapper.insert(qrChnFlowGt);
        return qrChnFlowGt;
    }

    /**
     * 更新渠道流水
     *
     * @param qrChnFlowGt
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public QrChnFlowGt updateQrFlow(QrChnFlowGt qrChnFlowGt) {
        qrChnFlowGt.setUpdTime(new Date());
        EntityWrapper<QrChnFlowGt> entityWrapper = new EntityWrapper<QrChnFlowGt>();
        Date creTime = qrChnFlowGt.getCreTime();
        entityWrapper.eq("QR_CHN_FLOW_GT_ID",qrChnFlowGt.getQrChnFlowGtId());
        //时间从0点到24点
        if (null != creTime) {
            entityWrapper.ge("cre_time", DateUtils.getInstall().nowDate(creTime));
            entityWrapper.lt("cre_time",DateUtils.getInstall().nextDate(creTime));
        }
        baseMapper.update(qrChnFlowGt,entityWrapper);
        return qrChnFlowGt;
    }

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     *
     * @param payNo
     * @param orgNo
     * @param transDate
     * @return
     */
    @Override
    public QrChnFlowGt findByPayNo(String payNo, String orgNo, String transDate) {
        Assert.notNull(payNo, "payNo should not be null!");
//        Assert.notNull(orgNo, "orgNo should not be null!");
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        EntityWrapper<QrChnFlowGt> entityWrapper = new EntityWrapper<QrChnFlowGt>();
        entityWrapper.eq("PAY_NO", payNo);
        if (StringUtils.isNotBlank(orgNo)) {
            entityWrapper.eq("ORG_NO", orgNo);
        }

        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date), "yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)), "yyyy-MM-dd HH:mm:ss"));
        }
        List<QrChnFlowGt> qrChnFlowGts = baseMapper.selectList(entityWrapper);
        if (null != qrChnFlowGts && 0 < qrChnFlowGts.size()) {
            return qrChnFlowGts.get(0);
        }
        return null;
    }
}
