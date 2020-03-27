package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;

/**
 * <p>
 * 平安扫码渠道流水表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
public interface QrChnFlowPaService extends IService<QrChnFlowPa> {
    /**
     * 新增渠道流水
     * @param qrChnFlowPa
     * @return
     */
    QrChnFlowPa inserQrFow(QrChnFlowPa qrChnFlowPa);

    /**
     * 更新渠道流水
     * @param qrChnFlowPa
     * @return
     */
    QrChnFlowPa updateQeFlow(QrChnFlowPa qrChnFlowPa);

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     * @param payNo
     * @param orgNo
     * @param transDate
     * @return
     */
    QrChnFlowPa findBypayNo(String payNo,String orgNo, String transDate);

}
