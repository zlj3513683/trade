package com.posppay.newpay.modules.sdk.starpos.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author zengjw
 *  公众号支付
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradePubAuthPayReq extends BaseTradeReq {
    /**
     * 接口类型
     */
    private String type;
    /**
     * 机构号
     */
    private String orgNo;
    /**
     * 设备号
     */
    private String trmNo;
    /**
     * 设备端交易时间
     */
    private String txnTime;
    /**
     * 授权code
     */
    private String code;
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
     * 订单标题
     */
    private String subject;
    /**
     * 订单号
     */
    private String selOrderNo;
    /**
     * 优惠说明
     */
    @JsonProperty("goods_tag")
    private String goodsTag;
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 附加字段
     */
    private String attach;
    /**
     * 支付公众号id
     */
    private String txnappid;
    /**
     * 终端流水号
     */
    private String orderId;
}
