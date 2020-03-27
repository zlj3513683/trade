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
public class TradeScanRefundReq extends BaseTradeReq {
    /**
     * 接口类型
     */
    private String type;
    /**
     * 终端编号
     */
    private String terminalNo;
    /**
     * 设备sn
     */
    private String sn;
    /**
     * 终端流水号
     */
    private String orderId;

    /**
     * 退款金额
     */
    private String refAmt;
    /**
     * 批次号
     */
    private String batchNo;

    /**
     * I-智能POS
     * A- app扫码
     * C-PC收银端
     * T-台牌扫码
     */
    private String txnCnl;
    /**
     * 平台凭证号
     */
    private String orderNo;


}
