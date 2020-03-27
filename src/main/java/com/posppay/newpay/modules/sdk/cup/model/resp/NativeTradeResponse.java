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
 * 功能：c2b响应
 *
 * @author 2019/7/23
 * @author zoulinjun
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NativeTradeResponse extends PaymentResponse {

    /**
     * 二维码链接
     */
    @JSONField(name= "code_url")
    @XmlElement(name = "code_url")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String codeUrl;

    /**
     * 二维码图片
     */
    @JSONField(name= "code_img_url")
    @XmlElement(name = "code_img_url")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String codeImgUrl;


}
