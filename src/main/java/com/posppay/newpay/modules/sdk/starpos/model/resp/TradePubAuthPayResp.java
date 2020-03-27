package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * 公众号支付
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradePubAuthPayResp extends BaseTradeResp {
    /**
     * 接口类型pubSigQry
     */
    private String type;
    /**
     * 系统交易时间
     */
    private String sysTime;
    /**
     * 系统流水号
     */
    @JsonProperty("LogNo")
    private String logNo;
    /**
     * 交易结查
     * A-等待授权
     * Z-交易未知
     */
    private String result;
    /**
     * 支付渠道订单号
     */
    private String orderNo;
    /**
     * 实付金额
     */
    private String amount;
    /**
     * 订单总金额
     */
    @JsonProperty("total_amount")
    private String totalAmount;
    /**
     * 预支付 ID
     */
    private String prepayId;
    /**
     * 支付公众号 ID
     */
    private String apiAppid;
    /**
     * 支付时间戳
     */
    private String apiTimestamp;
    /**
     * 支付随机字符串
     */
    private String apiNoncestr;
    /**
     * 订单详情扩展字符串
     */
    private String apiPackage;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 订单号
     */
    private String selOrderNo;
    /**
     * 订单优惠说明
     */
    private String goodsTag;
    /**
     * 附加字段
     */
    private String attach;

}
