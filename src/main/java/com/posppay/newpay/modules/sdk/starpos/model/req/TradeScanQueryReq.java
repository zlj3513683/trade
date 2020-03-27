package com.posppay.newpay.modules.sdk.starpos.model.req;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zengjw
 * B扫C退货请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradeScanQueryReq extends BaseTradeReq {
    /**
     * 接口类型
     */
    private String type;
    /**
     * 终端编号
     */
    private String terminalNo;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 第三方凭证号
     */
    private String orderNo;

    /**
     * 终端流水号
     */
    private String orderId;

    /**
     * 原始交易流水号
     */
    private String oldTxnLogId;



}
