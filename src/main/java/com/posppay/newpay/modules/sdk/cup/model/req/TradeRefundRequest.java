package com.posppay.newpay.modules.sdk.cup.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.cup.util.XmlUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C交易退款请求参数
 * @author MaoNing
 * @date 2019/5/5
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Accessors(chain = true)
public class TradeRefundRequest extends PaymentRequest{

    /**
     * 银联商户号
     */
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String merchantId;

    /**
     * 本平台商户订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     * 银联平台订单号
     */
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String transactionId;

    /**
     * 商户退款单号
     */
    @JSONField(name = "out_refund_no")
    @XmlElement(name = "out_refund_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outRefundNo;

    /**
     * 总金额
     */
    @JSONField(name = "total_fee")
    @XmlElement(name = "total_fee")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String totalFee;

    /**
     * 退款金额
     */
    @JSONField(name = "refund_fee")
    @XmlElement(name = "refund_fee")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundFee;

    /**
     * 操作员
     */
    @JSONField(name = "op_user_id")
    @XmlElement(name = "op_user_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String operatorId;

    /**
     * 退款渠道
     */
    @JSONField(name = "refund_channel")
    @XmlElement(name = "refund_channel")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String refundChannel;
    /**
     * 机构号
     */
    @JSONField(name = "sign_agentno")
    @XmlElement(name = "sign_agentno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signAgentNo;
}
