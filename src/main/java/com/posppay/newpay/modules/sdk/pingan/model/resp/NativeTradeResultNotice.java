package com.posppay.newpay.modules.sdk.pingan.model.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.pingan.util.XmlUtils;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * C扫B银联交易结果通知
 * @author MaoNing
 * @date 2019/5/13
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NativeTradeResultNotice extends PaymentResponse {

    /**
     *用户标识
     */
    @JSONField(name = "openid")
    @XmlElement(name = "openid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String openId;

    /**
     *交易类型
     */
    @JSONField(name = "trade_type")
    @XmlElement(name = "trade_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String tradeType;

    /**
     *是否关注公众账号
     */
    @JSONField(name = "is_subscribe")
    @XmlElement(name = "is_subscribe")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String subscribe;

    /**
     *支付结果
     */
    @JSONField(name = "pay_result")
    @XmlElement(name = "pay_result")
    private Integer payResult;

    /**
     *平台订单号
     */
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String transactionId;

    /**
     *第三方订单号
     */
    @JSONField(name = "out_transaction_id")
    @XmlElement(name = "out_transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTransactionId;

    /**
     *是否关注商户公众号
     */
    @JSONField(name = "sub_is_subscribe")
    @XmlElement(name = "sub_is_subscribe")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String subSubscribe;

    /**
     *商户appid
     */
    @JSONField(name = "sub_appid")
    @XmlElement(name = "sub_appid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String subAppId;

    /**
     *用户openid
     */
    @JSONField(name = "sub_openid")
    @XmlElement(name = "sub_openid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String subOpenId	;

    /**
     *商户订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     *总金额
     */
    @JSONField(name = "total_fee")
    @XmlElement(name = "total_fee")
    private Integer totalFee;

    /**
     *现金支付金额
     */
    @JSONField(name = "cash_fee")
    @XmlElement(name = "cash_fee")
    private Integer cashFee;

    /**
     *现金券金额
     */
    @JSONField(name = "coupon_fee")
    @XmlElement(name = "coupon_fee")
    private Integer couponFee;

    /**
     *货币种类
     */
    @JSONField(name = "fee_type")
    @XmlElement(name = "fee_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String feeType;

    /**
     *附加信息
     */
    @JSONField(name = "attach")
    @XmlElement(name = "attach")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String attach;

    /**
     *付款银行
     */
    @JSONField(name = "bank_type")
    @XmlElement(name = "bank_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String bankType;

    /**
     *银行订单号
     */
    @JSONField(name = "bank_billno")
    @XmlElement(name = "bank_billno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String bankBillNo;

    /**
     *支付完成时间
     */
    @JSONField(name = "time_end")
    @XmlElement(name = "time_end")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String timeEnd;

    /**
     *银联交易主键
     */
    @JSONField(name = "settle_key")
    @XmlElement(name = "settle_key")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String settleKey;

}
