package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * 公众号查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradePubAuthQueryResp extends BaseTradeResp {
    /**
     * 接口类型pubSigQry
     */
    private String type;
    /**
     * 微信公众账号
     */
    private String appId;
    /**
     * 微信公众号密钥
     */
    private String appIdKey;


}
