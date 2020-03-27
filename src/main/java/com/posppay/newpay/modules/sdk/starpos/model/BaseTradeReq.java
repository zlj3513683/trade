package com.posppay.newpay.modules.sdk.starpos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * 交易公共请求参数
 */
@Data
@Accessors(chain = true)
public class BaseTradeReq {
    /**
     * 字符集
     */
    private String characterSet;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 商户请求号
     */
    private String requestId;
    /**
     * 签名方式MD5
     */
    private String signType;
    /**
     * 版本号
     */
    private String version;
    /**
     * 签名数据
     */
    private String hmac;
    /**
     * 操作员号
     */
    private String oprId;
    /**
     * 接入号码
     */
    private String telNo;
    /**
     * 机构号
     */
    private String orgNo;
}
