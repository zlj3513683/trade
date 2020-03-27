package com.posppay.newpay.modules.sdk.pingan.model.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C退款响应数据
 * @author MaoNing
 * @date 2019/5/7
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class TradeRefundResponse extends PaymentResponse{

    /**
     * 平台订单号
     */
    @JSONField(name= "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String transactionId;
    /**
     * 平台订单号
     */
    @JSONField(name= "out_transaction_id")
    @XmlElement(name = "out_transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTransactionId;

    /**
     * 商户订单号
     */
    @JSONField(name= "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    @JSONField(name= "out_refund_no")
    @XmlElement(name = "out_refund_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outRefundNo;

    /**
     * 平台退款单号
     */
    @JSONField(name= "refund_id")
    @XmlElement(name = "refund_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundId;

    /**
     * 退款渠道
     */
    @JSONField(name= "refund_channel")
    @XmlElement(name = "refund_channel")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundChannel;

    /**
     * 退款金额
     */
    @JSONField(name= "refund_fee")
    @XmlElement(name = "refund_fee")
    private Integer refundFee;

    /**
     * 现金券退款金额
     */
    @JSONField(name= "coupon_refund_fee")
    @XmlElement(name = "coupon_refund_fee")
    private Integer couponRefundFee;
    /**
     * 交易类型
     */
    @JSONField(name= "trade_type")
    @XmlElement(name = "trade_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String tradeType;
}
