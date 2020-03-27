package com.posppay.newpay.model;

import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.ISOAmountFormatter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

import java.math.BigDecimal;

/**
 * 功能： 消费请求实体
 * @author zengjw
 */
@Data
@NoArgsConstructor
public class ConsumeTestRequest  {
    /**
     * 商品描述
     */

    private String body;
    /**
     * 非必传 json字符串
     */

    private String goodsDetail;
    /**
     * 商品标记
     */

    private String goodsTag;
    /**
     * 订单金额
     * TODo  以分为单位
     */

    private String amount;

    /**
     * 用户付款码
     */

    private String authCode;

    /**
     * 订单总金额
     */

    private String totalAmt;
    /**
     * 货币代码 写死156
     */

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

    private String payChannel;
    /**
     * 前置交易时间yyyyMMddHHmmss
     */

    private String transTime;
    /**
     * 商家自定义数据
     */

    private String attach;

}
