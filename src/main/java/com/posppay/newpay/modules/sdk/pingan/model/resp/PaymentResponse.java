package com.posppay.newpay.modules.sdk.pingan.model.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * @author MaoNing
 * @date 2019/4/29
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PaymentResponse {

    /**
     * 网关返回码
     */
    @JSONField(name= "code")
    @XmlElement(name = "code")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String code;

    /**
     * appId
     */
    @JSONField(name= "appid")
    @XmlElement(name = "appid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String appId;

    /**
     * 版本号
     */
    @JSONField(name= "version")
    @XmlElement(name = "version")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String version;

    /**
     * 字符集
     */
    @JSONField(name= "charset")
    @XmlElement(name = "charset")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String charset;

    /**
     * 签名方式
     */
    @JSONField(name= "sign_type")
    @XmlElement(name = "sign_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signType;

    /**
     * 返回状态码(请求状态码)
     */
    @JSONField(name= "status")
    @XmlElement(name = "status")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String status;

    /**
     * 返回错误信息
     */
    @JSONField(name= "message")
    @XmlElement(name = "message")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String message;

    /**
     * 当status为0是返回一下信息
     */



    /**
     * 业务结果
     */
    @JSONField(name= "result_code")
    @XmlElement(name = "result_code")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String resultCode;

    /**
     * 银联商户号
     */
    @JSONField(name= "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String merchantId;

    /**
     * 终端设备号
     */
    @JSONField(name= "device_info")
    @XmlElement(name = "device_info")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @JSONField(name= "nonce_str")
    @XmlElement(name = "nonce_str")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String nonceString;

    /**
     * 错误代码
     */
    @JSONField(name= "err_code")
    @XmlElement(name = "err_code")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String errorCode;

    /**
     * 错误代码描述
     */
    @JSONField(name= "err_msg")
    @XmlElement(name = "err_msg")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String errorMessage;

    /**
     * 签名
     */
    @JSONField(name= "sign")
    @XmlElement(name = "sign")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String sign;
}
