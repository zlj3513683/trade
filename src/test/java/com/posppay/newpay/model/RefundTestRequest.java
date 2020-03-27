package com.posppay.newpay.model;

import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.ISOAmountFormatter;
import lombok.*;
import net.sf.json.JSONObject;

import java.math.BigDecimal;

/**
 * 功能： 退货请求实体
 * @author zengjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundTestRequest  {
    /**
     * 原始订单号
     */

    private String originOrderNo;
    /**
     * 微信支付宝扫码查询的码
     */

    private String oriOutTransactionId;
    /**
     * 平台流水号
     */

    private String oriPayNo;

    /**
     * 退款金额
     */

    private String refAmt;
    /**
     * 原始交易日期
     */

    private String oriTransDate;
    /**
     * 当前交易时间
     */

    private String transTime;
    /**
     * 商家自定义数据
     */

    private String attach;

}
