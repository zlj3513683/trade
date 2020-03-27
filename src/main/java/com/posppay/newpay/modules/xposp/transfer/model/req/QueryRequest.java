package com.posppay.newpay.modules.xposp.transfer.model.req;

import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.DateFormatter;
import lombok.*;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 功能： 查询请求实体
 * @author zengjw
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class QueryRequest extends AbstractTransferRequest {

    public QueryRequest(JSONObject json) {
        super(json);
    }

    /**
     * 下游商户订单号
     */
    @TransParam
    private String oriOutOrderNo;
    /**
     * 原交易日期
     */
    @TransParam(formatter = DateFormatter.class,formatPattern = "yyyyMMdd")
    private Date oriTransDate;
    /**
     * 微信支付宝扫码查询的码
     */
    @TransParam
    private String oriOutTransactionId;
    /**
     * 平台流水号
     */
    @TransParam
    private String oriPayNo;

    private String body;
//

}
