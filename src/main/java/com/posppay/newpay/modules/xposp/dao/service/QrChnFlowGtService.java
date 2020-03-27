package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowGt;

/**
 * <p>
 * 星pos扫码渠道流水表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-07-18
 */
public interface QrChnFlowGtService extends IService<QrChnFlowGt> {
    /**
     * 新增渠道流水
     * @param qrChnFlowGt
     * @return
     */
    QrChnFlowGt inserQrFow(QrChnFlowGt qrChnFlowGt);

    /**
     * 更新渠道流水
     * @param qrChnFlowGt
     * @return
     */
    QrChnFlowGt updateQrFlow(QrChnFlowGt qrChnFlowGt);

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     * @param payNO
     * @param orgNo
     * @param transDate
     * @return
     */
    QrChnFlowGt findByPayNo(String payNo,String orgNo, String transDate);
}
