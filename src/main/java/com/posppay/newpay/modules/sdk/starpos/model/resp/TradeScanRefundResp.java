package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zengjw
 * B扫C退款请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradeScanRefundResp extends BaseTradeResp {
    /**
     * 接口类型sdkBarcodePay
     */
    private String type;
    /**
     * 订单号
     */
    private String result;
    /**
     * 余额
     */
    private String balance;
    /**
     * 金额
     */
    private String amount;



}
