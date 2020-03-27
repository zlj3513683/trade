package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.mapper.QrChnFlowPaDao;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowPaService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;
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
 * 平安扫码渠道流水表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Service
public class QrChnFlowPaServiceImpl extends ServiceImpl<QrChnFlowPaDao, QrChnFlowPa> implements QrChnFlowPaService {
    @Override
    public QrChnFlowPa inserQrFow(QrChnFlowPa qrChnFlowPa) {
        String qrChnFlowPaId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        qrChnFlowPa.setQrChnFlowPaId(qrChnFlowPaId);
        qrChnFlowPa.setCreTime(new Date());
        baseMapper.insert(qrChnFlowPa);
        return qrChnFlowPa;
    }

    /**
     * 更新渠道流水
     *
     * @param qrChnFlowPa
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public QrChnFlowPa updateQeFlow(QrChnFlowPa qrChnFlowPa) {
        qrChnFlowPa.setUpdTime(new Date());
        EntityWrapper<QrChnFlowPa> entityWrapper = new EntityWrapper<QrChnFlowPa>();
        Date creTime = qrChnFlowPa.getCreTime();
        entityWrapper.eq("QR_CHN_FLOW_PA_ID",qrChnFlowPa.getQrChnFlowPaId());
        //时间从0点到24点
        if (null != creTime) {
            entityWrapper.ge("cre_time", DateUtils.getInstall().nowDate(creTime));
            entityWrapper.lt("cre_time",DateUtils.getInstall().nextDate(creTime));
        }
        baseMapper.update(qrChnFlowPa,entityWrapper);
        return qrChnFlowPa;
    }

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     *
     * @param payNo
     * @param orgNo
     * @return
     */
    @Override
    public QrChnFlowPa findBypayNo(String payNo, String orgNo, String transDate) {
        Assert.notNull(payNo, "payNo should not be null!");
//        Assert.notNull(orgNo, "orgNo should not be null!");
        if (StringUtils.isBlank(transDate)) {
            //如果下游不上送时间则认为是当前日期
            transDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        DateTime date = DateUtil.parse(transDate, "yyyyMMdd");
        EntityWrapper<QrChnFlowPa> entityWrapper = new EntityWrapper<QrChnFlowPa>();
        entityWrapper.eq("OUT_TRADE_NO", payNo);
        if (StringUtils.isNotBlank(orgNo)) {
            entityWrapper.eq("ORG_NO", orgNo);
        }

        if (null != transDate) {
            entityWrapper.ge("cre_time", DateUtil.parse(DateUtils.dateTimeToString(date), "yyyy-MM-dd HH:mm:ss"));
            entityWrapper.lt("cre_time", DateUtil.parse(DateUtils.dateTimeToString(DateUtils.getInstall().nextDate(date)), "yyyy-MM-dd HH:mm:ss"));
        }
        List<QrChnFlowPa> qrChnFlowPas = baseMapper.selectList(entityWrapper);
        if (null != qrChnFlowPas && 0 < qrChnFlowPas.size()) {
            return qrChnFlowPas.get(0);
        }
        return null;
    }
}
