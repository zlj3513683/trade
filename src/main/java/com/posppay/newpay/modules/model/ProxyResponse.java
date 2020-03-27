package com.posppay.newpay.modules.model;

import com.posppay.newpay.modules.xposp.aspect.AbstractXpospProxy;
import com.posppay.newpay.modules.xposp.aspect.ProxyParams;
import lombok.*;

import java.io.Serializable;

/**
 * 功能：公共返回入口
 * @author zengjw
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyResponse  extends AbstractXpospProxy {
    /**
     * 机构号
     */
    private String orgNo;
    /**
     * 请求方法
     */
    @ProxyParams
    private String type;
    /**
     * 签名方式
     */
    @ProxyParams
    private String signType;
    /**
     * 版本号
     */
    @ProxyParams
    private String version;
    /**
     * 6个0
     */
    @ProxyParams
    private String returnCode;
    /**
     * 返回信息
     */
    @ProxyParams
    private String returnMsg;
    /**
     * 签名数据
     */
    @ProxyParams
    private String hmac;
    private String respDetail;

}
