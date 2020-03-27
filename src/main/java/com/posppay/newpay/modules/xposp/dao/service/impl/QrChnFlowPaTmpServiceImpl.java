package com.posppay.newpay.modules.xposp.dao.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.mapper.QrChnFlowPaTmpDao;
import com.posppay.newpay.modules.xposp.dao.service.QrChnFlowPaTmpService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPaTmp;
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
 * @since 2019-06-18
 */
@Service
public class QrChnFlowPaTmpServiceImpl extends ServiceImpl<QrChnFlowPaTmpDao, QrChnFlowPaTmp> implements QrChnFlowPaTmpService {
    @Override
    public QrChnFlowPaTmp inserQrFow(QrChnFlowPaTmp qrChnFlowPa) {
        String qrChnFlowPaId = DateUtil.format(new Date(), "yyyyMmddHH") + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        qrChnFlowPa.setQrChnFlowPaId(qrChnFlowPaId);
        qrChnFlowPa.setCreTime(new Date());
        qrChnFlowPa.setUpdTime(new Date());
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
    public QrChnFlowPaTmp updateQeFlow(QrChnFlowPaTmp qrChnFlowPa) {
        qrChnFlowPa.setUpdTime(new Date());
        baseMapper.updateById(qrChnFlowPa);
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
    public QrChnFlowPaTmp findBypayNo(String payNo, String orgNo) {
        Assert.notNull(payNo, "payNo should not be null!");

        EntityWrapper<QrChnFlowPaTmp> entityWrapper = new EntityWrapper<QrChnFlowPaTmp>();
        entityWrapper.eq("OUT_TRADE_NO", payNo);
                if(StringUtils.isNotBlank(orgNo)){
                    entityWrapper.eq("ORG_NO", orgNo);
                }

        List<QrChnFlowPaTmp> qrChnFlowPaTmps = baseMapper.selectList(entityWrapper);
        if (null != qrChnFlowPaTmps && 0 != qrChnFlowPaTmps.size()) {
            return qrChnFlowPaTmps.get(0);
        }
        return null;
    }

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     *
     * @param payNo
     * @return
     */
    @Override
    public QrChnFlowPaTmp findBypayNo(String payNo) {
        Assert.notNull(payNo, "payNo should not be null!");
        EntityWrapper<QrChnFlowPaTmp> entityWrapper = new EntityWrapper<QrChnFlowPaTmp>();
        entityWrapper.eq("OUT_TRADE_NO", payNo);
        List<QrChnFlowPaTmp> qrChnFlowPaTmps = baseMapper.selectList(entityWrapper);
        if (null != qrChnFlowPaTmps && 0 != qrChnFlowPaTmps.size()) {
            return qrChnFlowPaTmps.get(0);
        }
        return null;
    }

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     *
     * @param payNo
     * @param orgNo
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deleteByPayNo(String payNo, String orgNo) throws Exception {
        Assert.notNull(payNo, "payNo should not be null!");
//        Assert.notNull(orgNo, "orgNo should not be null!");
        EntityWrapper<QrChnFlowPaTmp> entityWrapper = new EntityWrapper<QrChnFlowPaTmp>();
        if (StringUtils.isNotBlank(orgNo)){
            entityWrapper.eq("ORG_NO", orgNo);
        }
        entityWrapper.eq("OUT_TRADE_NO", payNo);
        baseMapper.delete(entityWrapper);
    }
}
