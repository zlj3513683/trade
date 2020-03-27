package com.posppay.newpay.modules.xposp.router.channel.cup.model.resp;

import lombok.Data;

/**
 * @author zengjw
 * 功能：银联C扫B返回实体
 */
@Data
public class RefundSdkResponse {

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
     * 银联退款单号
     */
    private String cupRefundNo;
}
