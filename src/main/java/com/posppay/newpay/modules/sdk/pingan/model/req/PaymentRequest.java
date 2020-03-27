package com.posppay.newpay.modules.sdk.pingan.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 支付请求基类
 * @author MaoNing
 * @date 2019/4/29
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@Accessors(chain = true)
public class PaymentRequest {

    /**
     * 接口类型
     */
    @JSONField(name = "service")
    @XmlElement(name = "service")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String service;

    /**
     * 版本号
     */
    @JSONField(name = "version")
    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String version;

    /**
     * 字符集
     */
    @JSONField(name = "charset")
    @XmlElement(name = "charset")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String charset;

    /**
     * 签名方式
     */
    @JSONField(name = "sign_type")
    @XmlElement(name = "sign_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signType;

    /**
     * 随机字符串
     */
    @JSONField(name = "nonce_str")
    @XmlElement(name = "nonce_str")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String nonceString;

    /**
     * 签名
     */
    @JSONField(name = "sign")
    @XmlElement(name = "sign")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String sign;

    public PaymentRequest() {
        this.charset = "UTF-8";
        this.version = PinganConstant.DEFAULT_VERSION;
        this.signType = PinganConstant.SIGN_TYPE;
    }
}
