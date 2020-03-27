package com.posppay.newpay.modules.xposp.transfer.model.req;

import com.posppay.newpay.modules.model.ProxyRequest;
import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.DateFormatter;
import com.shouft.newpay.base.formater.ISOAmountFormatter;
import lombok.*;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能： 退货请求实体
 * @author zengjw
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequest extends AbstractTransferRequest {

    public RefundRequest(JSONObject json) {
        super(json);
    }

    /**
     * 原始订单号
     */
    @TransParam
   private String oriOutOrderNo;
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

    /**
     * 退款金额
     */
    @TransParam(formatter = ISOAmountFormatter.class)
    private BigDecimal refAmt;
    /**
     * 原始交易日期
     */
    @TransParam(formatter = DateFormatter.class, formatPattern = "yyyyMMdd" )
    private Date oriTransDate;
    /**
     * 当前交易时间
     */
    @TransParam(formatter = DateFormatter.class, formatPattern = "yyyyMMddHHmmss" )
   private Date transTime;
    /**
     * 商家自定义数据
     */
    @TransParam
    private String attach;
    private String chnCode;
    private String subTransType;
    private String payChannel;
    private BigDecimal orgFee;
    private BigDecimal mrchRateAmt;
    private BigDecimal orgFeeAmt;
    private BigDecimal chnFee;
    private BigDecimal chnFeeAmt;

}
