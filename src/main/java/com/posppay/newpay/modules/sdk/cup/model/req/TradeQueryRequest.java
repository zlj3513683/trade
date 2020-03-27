package com.posppay.newpay.modules.sdk.cup.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.cup.util.XmlUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C支付结果查询请求参数
 * @author MaoNing
 * @date 2019/4/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Accessors(chain = true)
public class TradeQueryRequest extends PaymentRequest{

    /**
     *银联商户号
     */
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String merchantId;

    /**
     *银联订单号
     */
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String transactionId;

    /**
     *本平台订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;
    /**
     * 机构号
     */
    @JSONField(name = "sign_agentno")
    @XmlElement(name = "sign_agentno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signAgentNo;
}
