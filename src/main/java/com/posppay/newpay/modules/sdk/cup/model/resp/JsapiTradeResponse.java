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
 * 功能：
 *
 * @author 2019/7/24
 * @author zoulinjun
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class JsapiTradeResponse extends PaymentResponse  {

    /**
     * 授权口令
     */
    @JSONField(name= "token_id")
    @XmlElement(name = "token_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String tokenId;

    /**
     * 原生态js支付信息或小程序支付信息
     */
    @JSONField(name= "pay_info")
    @XmlElement(name = "pay_info")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String payInfo;


}
