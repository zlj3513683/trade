package com.posppay.newpay.modules.xposp.router.channel.cup.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能： 银联C扫B请求实体
 * @author zengjw
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RefundSdkRequest {
//    操作员  operatorId

    /**
     * 银联商户号
      */
    private String merchantId;
    /**
     * 银联订单号
     */
    private String transactionId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 总金额
     */
    private String totalFee;
    /**
     * 退款金额
     */
    private String refundFee;
    /**
     * 授权码
     */
    private String operatorId;

}

