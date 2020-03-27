package com.posppay.newpay.modules.xposp.router.channel.cup.model.resp;

import lombok.Data;

/**
 * @author zengjw
 * 功能：银联扫码查询返回实体
 */
@Data
public class ScanQuerySdkResponse {

    /**
     *  返回码
     */
    private String returnCode;
    /**
     * 错误描述
     */
    private String messageData;
    /**
     * 交易状态
     */
    private String result;
    /**
     * 银联订单号
     */
    private String orderNo;
    /**
     * 第三方订单号
     */
    private String channelId;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 总金额
     */
    private String totalFee;
}
