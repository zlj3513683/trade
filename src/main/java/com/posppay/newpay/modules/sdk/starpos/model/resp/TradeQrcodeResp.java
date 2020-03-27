package com.posppay.newpay.modules.sdk.starpos.model.resp;

import com.posppay.newpay.modules.sdk.starpos.model.BaseTradeResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zengjw
 * B扫C交易请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TradeQrcodeResp extends BaseTradeResp {
    /**
     * 接口类型sdkBarcodePosPay
     */
    private String type;
    /**
     * 交易结果
     */
    private String result;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 动态码地址
     */
    private String qrCode;


}
