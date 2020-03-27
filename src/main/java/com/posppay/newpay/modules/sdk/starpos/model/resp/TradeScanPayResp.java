package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeReq;
import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zengjw
 * B扫C交易请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradeScanPayResp extends BaseTradeResp {
    /**
     * 接口类型sdkBarcodePay
     */
    private String type;
    /**
     * 交易结果
     */
    private String result;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 交易时间
     */
    private String payTime;
    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付类型
     * 1.条码支付,2.声波支付,3.二维码支付, 4.线上支付
     */
    private String payType;
    /**
     * 终端流水号
     */
    private String orderId;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 订单金额
     */
    private String amount;



}
