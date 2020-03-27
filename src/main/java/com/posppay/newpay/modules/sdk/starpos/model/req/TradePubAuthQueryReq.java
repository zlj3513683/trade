package com.posppay.newpay.modules.sdk.starpos.model.req;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author zengjw
 *  公众号查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradePubAuthQueryReq extends BaseTradeReq {
    /**
     * 接口类型
     */
    private String type;
    /**
     * 机构号
     */
    private String orgNo;
    /**
     * 设备号
     */
    private String trmNo;
    /**
     * 设备端交易时间
     */
    private String txnTime;
    /**
     * 附加字段
     */
    private String attach;

}
