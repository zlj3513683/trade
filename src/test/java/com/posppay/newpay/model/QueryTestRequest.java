package com.posppay.newpay.model;

import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

/**
 * 功能： 查询请求实体
 * @author zengjw
 */
@Data
@NoArgsConstructor
public class QueryTestRequest  {

    /**
     * 下游商户订单号
     */

    private String oriOutOrderNo;
    /**
     * 原交易日期
     */

    private String oriTransDate;
    /**
     * 微信支付宝扫码查询的码
     */

    private String orioutTransactionId;
    /**
     * 平台流水号
     */

    private String oriPayNo;

}
