package com.posppay.newpay.modules.xposp.transfer.model.req;

import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.BigDecimalFormatter;
import com.shouft.newpay.base.formater.CurrencyFormatter;
import com.shouft.newpay.base.formater.DateFormatter;
import com.shouft.newpay.base.formater.ISOAmountFormatter;
import com.shouft.newpay.base.utils.Currency;
import lombok.*;
import net.sf.json.JSONObject;
import org.joda.time.format.ISODateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能： 消费请求实体
 * @author zengjw
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ConsumeRequest extends AbstractTransferRequest {
    public ConsumeRequest(JSONObject json) {
        super(json);
    }
    /**
     * 商品描述
     */
    @TransParam
    private String body;
    /**
     * 非必传 json字符串
     */
    @TransParam
    private String goodsDetail;
    /**
     * 商品标记
     */
    @TransParam
    private String goodsTag;
    /**
     * 订单金额
     * TODo  以分为单位
     */
    @TransParam(formatter = ISOAmountFormatter.class)
    private BigDecimal amount;

    /**
     * 用户付款码
     */
    @TransParam
    private String authCode;

    /**
     * 订单总金额
     */
    @TransParam
    private String totalAmt;
    /**
     * 货币代码 写死156
     */
    @TransParam
    private String currency;
    /*todo 我们系统写死
     交易渠道
     I-智能POS
     A- app扫码
     C-PC收银端
     T-台牌扫 */
//    private String txnCnl;

    /**
     * 对应的码 支付宝微信云闪付
     */
    @TransParam
    private String payChannel;
    /**
     * 前置交易时间yyyyMMddHHmmss
     */
    @TransParam(formatter = DateFormatter.class, formatPattern = "yyyyMMddHHmmss" )
    private Date transTime;
    /**
     * 商家自定义数据
     */
    @TransParam
    private String attach;




}
