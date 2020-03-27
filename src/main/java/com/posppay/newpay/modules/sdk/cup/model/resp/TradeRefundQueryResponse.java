package com.posppay.newpay.modules.sdk.cup.model.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.cup.util.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C交易退款结果查询请求参数
 * @author MaoNing
 * @date 2019/5/7
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class TradeRefundQueryResponse extends PaymentResponse{

    /**
     * 银联订单号
     */
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String transactionId;

    /**
     * 本平台订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     * 退款笔数
     */
    @JSONField(name = "refund_count")
    @XmlElement(name = "refund_count")
    private Integer refundCount;

    /**
     * 本平台退款单号
     */
    @JSONField(name = "out_refund_no_0")
    @XmlElement(name = "out_refund_no_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outRefundNo;

    /**
     * 银联退款单号
     */
    @JSONField(name = "refund_id_0")
    @XmlElement(name = "refund_id_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundId;

    /**
     * 退款渠道
     */
    @JSONField(name = "refund_channel_0")
    @XmlElement(name = "refund_channel_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundChannel;

    /**
     * 退款金额
     */
    @JSONField(name = "refund_fee_0")
    @XmlElement(name = "refund_fee_0")
    private Integer refundFee;

    /**
     * 现金券退款金额
     */
    @JSONField(name = "coupon_refund_fee_0")
    @XmlElement(name = "coupon_refund_fee_0")
    private Integer couponRefundFee;

    /**
     * 退款时间
     */
    @JSONField(name = "refund_time_0")
    @XmlElement(name = "refund_time_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundTime;

    /**
     * 退款状态
     */
    @JSONField(name = "refund_status_0")
    @XmlElement(name = "refund_status_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundStatus;

    /**
     * 银联交易主键
     */
    @JSONField(name = "settle_key_0")
    @XmlElement(name = "settle_key_0")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String settleKey;


}
