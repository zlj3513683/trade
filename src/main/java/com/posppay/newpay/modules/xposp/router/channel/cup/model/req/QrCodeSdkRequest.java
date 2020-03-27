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
public class QrCodeSdkRequest {
//    操作员  operatorId

    /**
     * 银联商户号
      */
    private String merchantId;
    /**
     * 本平台商户订单号
     */
    private String outTradeNo;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 总金额
     */
    private String totalFee;
    /**
     * 授权码
     */
    private String operatorId;

}

