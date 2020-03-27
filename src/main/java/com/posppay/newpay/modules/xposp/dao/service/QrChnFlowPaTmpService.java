package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPa;
import com.posppay.newpay.modules.xposp.entity.QrChnFlowPaTmp;

/**
 * <p>
 * 平安扫码渠道流水表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-18
 */
public interface QrChnFlowPaTmpService extends IService<QrChnFlowPaTmp> {
    /**
     * 新增渠道流水
     * @param qrChnFlowPa
     * @return
     */
    QrChnFlowPaTmp inserQrFow(QrChnFlowPaTmp qrChnFlowPa);

    /**
     * 更新渠道流水
     * @param qrChnFlowPa
     * @return
     */
    QrChnFlowPaTmp updateQeFlow(QrChnFlowPaTmp qrChnFlowPa);

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     * @param payNo
     * @param orgNo
     * @return
     */
    QrChnFlowPaTmp findBypayNo(String payNo,String orgNo);

    /**
     * 根据凭证号和机构号来获取唯一一条流水
     * @param payNo
     * @return
     */
    QrChnFlowPaTmp findBypayNo(String payNo);
    /**
     * 根据凭证号和机构号来获取唯一一条流水
     * @param payNo
     * @param orgNo
     * @return
     */
    void deleteByPayNo(String payNo,String orgNo)throws Exception;

}
