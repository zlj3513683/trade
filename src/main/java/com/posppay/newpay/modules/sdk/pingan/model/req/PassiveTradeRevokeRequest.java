package com.posppay.newpay.modules.sdk.pingan.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * B扫C交易撤销
 * @author MaoNing
 * @date 2019/4/30
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PassiveTradeRevokeRequest extends PaymentRequest{

    /**
     *商户号
     */
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String merchantId;

    /**
     *商户订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String outTradeNo;
}
