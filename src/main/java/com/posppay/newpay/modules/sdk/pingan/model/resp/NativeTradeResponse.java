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
 * C扫B交易响应参数
 * @author MaoNing
 * @date 2019/5/8
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NativeTradeResponse extends PaymentResponse{

    /**
     * 二维码链接
     */
    @JSONField(name = "code_url")
    @XmlElement(name = "code_url")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String codeUrl;

    /**
     * 二维码图片
     */
    @JSONField(name = "code_img_url")
    @XmlElement(name = "code_img_url")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String codeUrlImage;
}
