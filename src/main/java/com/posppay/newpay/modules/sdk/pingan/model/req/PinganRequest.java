package com.posppay.newpay.modules.sdk.pingan.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求基类（非交易）
 * @author MaoNing
 * @date 2019/4/8
 */
@Data
public class PinganRequest {

    /**
     * 合作伙伴ID
     */
    @JSONField(name = "partner")
    private String partner;

    /**
     * 服务名称
     */
    @JSONField(name = "serviceName")
    private String serviceName;

    /**
     * 签名类型
     */
    @JSONField(name = "signType")
    private String signType;

    /**
     * 字符集
     */
    @JSONField(name = "charset")
    private String charset;

    /**
     * 请求数据(xml)
     */
    @JSONField(name = "data")
    private String data;

    /**
     * 数据类型
     */
    @JSONField(name = "dataType")
    private String dataType;

    /**
     * 数据签名
     */
    @JSONField(name = "dataSign")
    private String dataSign;

    public PinganRequest() {
        //this.partner = CupConstant.PARTNER;
        this.charset = PinganConstant.DEFAULT_CHARSET;
        this.dataType = PinganConstant.DATA_TYPE;
    }

    public boolean checkNull(){
        boolean flag = StringUtils.isBlank(this.partner) || StringUtils.isBlank(this.serviceName)
                || StringUtils.isBlank(this.charset) || StringUtils.isBlank(this.dataSign)
                || (StringUtils.isBlank(this.data)
                        && !PinganConstant.ServerName.PIC_UPLOAD.equalsIgnoreCase(this.serviceName));
        if (flag){
            return true;
        }
        return false;
    }
}
