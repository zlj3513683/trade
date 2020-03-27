package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * B扫C退款请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradeScanQueryResp extends BaseTradeResp {
    /**
     * 接口类型sdkBarcodePay
     */
    private String type;
    /**
     * 订单号
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
     * 1、支付宝
     * 2、微信
     * 3、银联二维码
     */
    private String payChannel;
    /**
     * 交易类型
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
     * 金额
     */
    private String amount;
    /**
     * 余额
     */
    private String balance;
    /**
     * 内部订单号
     */
    private String logNo;



}
