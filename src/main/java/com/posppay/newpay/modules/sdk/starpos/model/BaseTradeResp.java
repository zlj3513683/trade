package com.posppay.newpay.modules.sdk.starpos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * 交易公共请求参数
 */
@Data
@Accessors(chain = true)
public class BaseTradeResp {
    /**
     * 商户编号
     */
    private String merchantId;
    /**
     * 商户编号
     */
    private String mercId;
    /**
     * 加签数据
     */
    private String hmac;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 版本号
     */
    private String version;
    /**
     * 返回码
     */
    private String returnCode;
    /**
     * 小票订单号
     */
    @JsonProperty("ChannelId")
    private String channelId;
    /**
     * 返回码描述信息
     */
    private String message;

}
