package com.posppay.newpay.modules.xposp.router.channel.cup.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能： 银联扫码查询请求实体
 * @author zengjw
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScanQuerySdkRequest {
//    操作员  operatorId

    /**
     * 银联商户号
      */
    private String merchantId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;

}

