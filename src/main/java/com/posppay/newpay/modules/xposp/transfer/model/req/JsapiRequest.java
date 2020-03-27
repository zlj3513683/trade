package com.posppay.newpay.modules.xposp.transfer.model.req;

import com.posppay.newpay.modules.xposp.aspect.AbstractTransferRequest;
import com.posppay.newpay.modules.xposp.aspect.TransParam;
import com.shouft.newpay.base.formater.DateFormatter;
import com.shouft.newpay.base.formater.ISOAmountFormatter;
import lombok.*;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：
 *
 * @author 2019/7/24
 * @author zoulinjun
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsapiRequest extends AbstractTransferRequest {

    public JsapiRequest(JSONObject json) {
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
     * 订单总金额
     */
    @TransParam
    private String totalAmt;
    /**
     * 货币代码 写死156
     */
    @TransParam
    private String currency;

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
    /**
     * 公众号ID
     */
    @TransParam
    private String subAppid;
    /**
     * 通知地址
     */
    @TransParam
    private String notifyUrl;
    /**
     * 用户openid
     */
    @TransParam
    private String openid;


}
