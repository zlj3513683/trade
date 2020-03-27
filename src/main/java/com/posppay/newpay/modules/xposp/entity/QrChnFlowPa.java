package com.posppay.newpay.modules.xposp.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平安扫码渠道流水表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_QR_CHN_FLOW_PA")
public class QrChnFlowPa extends Model<QrChnFlowPa> {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    @TableId("QR_CHN_FLOW_PA_ID")
    private String qrChnFlowPaId;
    /**
     * 平安机构号
     */
    @TableField("ORG_NO")
    private String orgNo;
    /**
     * 平台商户号
     */
    @TableField("MRCH_NO")
    private String mrchNo;
    /**
     * 商户名称
     */
    @TableField("MRCH_NAME")
    private String mrchName;
    /**
     * 货币代码
     */
    @TableField("CURRENCY")
    private String currency;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("CRE_TIME")
    private Date creTime;
    /**
     * 更新时间
     */
    @TableField("UPD_TIME")
    private Date updTime;
    /**
     * 设备IP
     */
    @TableField("DEVICEIP")
    private String deviceip;
    /**
     * 平安商户号
     */
    @TableField("PA_MERC_ID")
    private String paMercId;
    /**
     * 操作员号
     */
    @TableField("OPRID")
    private String oprid;
    /**
     * 设备类型
     */
    @TableField("TRMNL_TYPE")
    private String trmnlType;
    /**
     * 商户订单号
     */
    @TableField("OUT_TRADE_NO")
    private String outTradeNo;
    /**
     * 设备端交易时间
     */
    @TableField("TXNTIME")
    private String txntime;
    /**
     * 响应码
     */
    @TableField("RESP_CODE")
    private String respCode;
    /**
     * 渠道系统交易时间
     */
    @TableField("SYS_TIME")
    private String sysTime;
    /**
     * 实付金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;
    /**
     * 订单总金额
     */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    /**
     * 支付授权码
     */
    @TableField("AUTH_CODE")
    private String authCode;
    /**
     * 交易渠道
     */
    @TableField("PAY_CHANNEL")
    private String payChannel;
    /**
     * 货物标记
     */
    @TableField("GOODS_TAG")
    private String goodsTag;
    /**
     * 商品描述
     */
    @TableField("BODYDETAIL")
    private String bodydetail;
    /**
     * 商品详细信息
     */
    @TableField("GOODS_DETAIL")
    private String goodsDetail;
    /**
     * 附加数据
     */
    @TableField("ATTACH")
    private String attach;
    /**
     * 是否需要电子发票
     */
    @TableField("NEED_RECEIPT")
    private String needReceipt;
    /**
     * 第三方订单号
     */
    @TableField("OUT_TRANSACTION_ID")
    private String outTransactionId;

    /**
     * 平安订单号
     */
    @TableField("TRANSACTION_ID")
    private String transactionId;
    /**
     * 用户是否关注子公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @TableField("SUB_IS_SUBSCRIBE")
    private String subIsSubscribe;
    /**
     * 子商户appId
     */
    @TableField("SUB_APPID")
    private String subAppid;
    /**
     * 开票金额
     */
    @TableField("INVOICE_AMOUNT")
    private BigDecimal invoiceAmount;
    /**
     * 支付完成时间
     */
    @TableField("TIME_END")
    private Date timeEnd;
    /**
     * 银行订单号
     */
    @TableField("BANK_BILLNO")
    private String bankBillno;
    /**
     * 交易子类型
     */
    @TableField("SUB_TRANS_TYPE")
    private String subTransType;
    /**
     * 用户是否关注公众账号，Y-关注，N-未关注
     */
    @TableField("IS_SUBSCRIBE")
    private String isSubscribe;
    /**
     * 集分宝付款金额(支付宝)
     */
    @TableField("POINT_AMOUNT")
    private BigDecimal pointAmount;
    /**
     * 现金券金额【微信】
     */
    @TableField("COUPON_FEE")
    private BigDecimal couponFee;
    /**
     * 优惠详情【微信]
     */
    @TableField("PROMOTION_DETAIL")
    private String promotionDetail;
    /**
     * 优惠详情【支付宝】
     */
    @TableField("DISCOUNT_GOODS_DETAIL")
    private String discountGoodsDetail;
    /**
     * 交易支付使用的资金渠道 【支付宝】
     */
    @TableField("FUND_BILL_LIST")
    private String fundBillList;
    /**
     * 实收金额【支付宝】
     */
    @TableField("RECEIPT_AMOUNT")
    private BigDecimal receiptAmount;
    /**
     * 买家实付金额【支付宝】
     */
    @TableField("BUYER_PAY_AMOUNT")
    private BigDecimal buyerPayAmount;
    /**
     * 付款银行
     */
    @TableField("BANK_TYPE")
    private String bankType;
    /**
     * 唯一识别
     */
    @TableField("UUID")
    private String uuid;
    /**
     * 免充值金额
     */
    @TableField("MDISCOUNT")
    private BigDecimal mdiscount;
    /**
     * 优惠详情【银联】
     */
    @TableField("UNIONPAY_DISCOUNT")
    private String unionpayDiscount;
    /**
     * 用户支付时间
     */
    @TableField("GMT_PAYMENT")
    private Date gmtPayment;
    /**
     * 买家支付宝用户ID【支付宝】
     */
    @TableField("BUYER_USER_ID")
    private String buyerUserId;
    /**
     * 买家支付宝账号【支付宝】
     */
    @TableField("BUYER_LOGON_ID")
    private String buyerLogonId;
    /**
     * 商户appid
     */
    @TableField("APPID")
    private String appid;
    /**
     * 用户在受理商户 appid 下的唯一标识
     */
    @TableField("OPENID")
    private String openid;
    /**
     * 用户在子商户appid下的唯一标识
     */
    @TableField("SUB_OPENID")
    private String subOpenid;
    /**
     * 退款笔数
     */
    @TableField("REFUND_COUNT")
    private Double refundCount;
    /**
     * 退款状态
     */
    @TableField("REFUND_STATUS")
    private String refundStatus;
    /**
     * 退款渠道
     */
    @TableField("REFUND_CHANNEL")
    private String refundChannel;
    /**
     * 退款金额
     */
    @TableField("REFUND_FEE")
    private BigDecimal refundFee;
    /**
     * 现金券退款金额
     */
    @TableField("COUPON_REFUND_FEE")
    private BigDecimal couponRefundFee;
    /**
     * 交易渠道
     I-智能POS
     A- app扫码
     C-PC收银端
     T-台牌扫
     */
    @TableField("TXN_CNL")
    private String txnCnl;
    /**
     *通知地址
     */
    @TableField("NOTIFY_URL")
    private String notifyUrl;


    @Override
    protected Serializable pkVal() {
        return this.qrChnFlowPaId;
    }

}
