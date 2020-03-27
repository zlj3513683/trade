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
 * B扫C支付结果查询参数
 * @author MaoNing
 * @date 2019/4/30
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class TradeQueryResponse extends PaymentResponse{

    /**
     *交易状态
     */
    @JSONField(name = "trade_state")
    @XmlElement(name = "trade_state")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String tradeState;

    /**
     *交易状态描述
     */
    @JSONField(name = "trade_state_desc")
    @XmlElement(name = "trade_state_desc")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String tradeStateDesc;

    /**
     *交易类型
     */
    @JSONField(name = "trade_type")
    @XmlElement(name = "trade_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String tradeType;

    /**
     *商户appid
     */
    @JSONField(name = "appid")
    @XmlElement(name = "appid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String appId;

    /**
     *子商户appid
     */
    @JSONField(name = "sub_appid")
    @XmlElement(name = "sub_appid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String subAppId;

    /**
     *用户在受理商户 appid 下的唯一标识
     */
    @JSONField(name = "openid")
    @XmlElement(name = "openid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String openId;

    /**
     *用户在子商户appid下的唯一标识
     */
    @JSONField(name = "sub_openid")
    @XmlElement(name = "sub_openid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String subOpenId;

    /**
     *是否关注公众账号
     */
    @JSONField(name = "is_subscribe")
    @XmlElement(name = "is_subscribe")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String subscribe;

    /**
     *是否关注子商户公众账号
     */
    @JSONField(name = "sub_is_subscribe")
    @XmlElement(name = "sub_is_subscribe")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String subSubscribe;

    /**
     *银联订单号
     */
    @JSONField(name = "transaction_id")
    @XmlElement(name = "transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String transactionId;

    /**
     *第三方交易号
     */
    @JSONField(name = "out_transaction_id")
    @XmlElement(name = "out_transaction_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String outTransactionId;

    /**
     *本平台订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String outTradeNo;

    /**
     *总金额
     */
    @JSONField(name = "total_fee")
    @XmlElement(name = "total_fee")
    private  Integer totalFee;

    /**
     *现金支付金额(微信)
     */
    @JSONField(name = "cash_fee")
    @XmlElement(name = "cash_fee")
    private  Integer cashFee ;

    /**
     *优惠详情(微信)
     */
    @JSONField(name = "promotion_detail")
    @XmlElement(name = "promotion_detail")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String promotionDetail;

    /**
     *优惠详情(银联)
     */
    @JSONField(name = "unionpay_discount")
    @XmlElement(name = "unionpay_discount")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String unionpayDiscount;

    /**
     *开票金额 (支付宝）
     */
    @JSONField(name = "invoice_amount")
    @XmlElement(name = "invoice_amount")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String invoiceAmount ;

    /**
     *现金券金额
     */
    @JSONField(name = "coupon_fee")
    @XmlElement(name = "coupon_fee")
    private  Integer couponFee;

    /**
     *货币种类
     */
    @JSONField(name = "fee_type")
    @XmlElement(name = "fee_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String feeType;

    /**
     *附加信息
     */
    @JSONField(name = "attach")
    @XmlElement(name = "attach")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String attach;

    /**
     *付款银行
     */
    @JSONField(name = "bank_type")
    @XmlElement(name = "bank_type")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String bankType;

    /**
     *银行订单号
     */
    @JSONField(name = "bank_billno")
    @XmlElement(name = "bank_billno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String bankBillNo;

    /**
     *支付完成时间
     */
    @JSONField(name = "time_end")
    @XmlElement(name = "time_end")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String timeEnd;

    /**
     *银联交易主键
     */
    @JSONField(name = "settle_key")
    @XmlElement(name = "settle_key")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private  String settleKey;

}
