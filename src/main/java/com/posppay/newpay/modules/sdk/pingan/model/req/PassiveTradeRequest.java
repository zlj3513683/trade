package com.posppay.newpay.modules.sdk.pingan.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C请求参数
 * @author MaoNing
 * @date 2019/4/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Accessors(chain = true)
public class PassiveTradeRequest extends PaymentRequest{

    /**
     * 银联商户号
     */
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String merchantId;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     * 商品描述
     */
    @JSONField(name = "body")
    @XmlElement(name = "body")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String body;

    /**
     * 总金额
     */
    @JSONField(name = "total_fee")
    @XmlElement(name = "total_fee")
    private Integer totalFee;

    /**
     * 订单生成的机器 IP
     */
    @JSONField(name = "mch_create_ip")
    @XmlElement(name = "mch_create_ip")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String machineIp;

    /**
     * 扫码支付授权码
     */
    @JSONField(name = "auth_code")
    @XmlElement(name = "auth_code")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String authCode;

    /**
     * 操作员
     */
    @JSONField(name = "op_user_id")
    @XmlElement(name = "op_user_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String operatorId;
    /**
     * 机构号
     */
    @JSONField(name = "sign_agentno")
    @XmlElement(name = "sign_agentno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signAgentNo;
}
