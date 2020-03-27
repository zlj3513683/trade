package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平安扫码渠道流水表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_QR_CHN_FLOW_PA_TMP")
public class QrChnFlowPaTmp extends Model<QrChnFlowPaTmp> {

    private static final long serialVersionUID = 1L;

    @TableField("QR_CHN_FLOW_PA_ID")
    private String qrChnFlowPaId;
    @TableField("ORG_NO")
    private String orgNo;
    @TableField("MRCH_NO")
    private String mrchNo;
    @TableField("MRCH_NAME")
    private String mrchName;
    @TableField("CURRENCY")
    private String currency;
    @TableField("REMARK")
    private String remark;
    @TableField("CRE_TIME")
    private Date creTime;
    @TableField("UPD_TIME")
    private Date updTime;
    @TableField("DEVICEIP")
    private String deviceip;
    @TableField("PA_MERC_ID")
    private String paMercId;
    @TableField("OPRID")
    private String oprid;
    @TableField("TRMNL_TYPE")
    private String trmnlType;
    @TableField("OUT_TRADE_NO")
    private String outTradeNo;
    @TableField("TXNTIME")
    private String txntime;
    @TableField("RESP_CODE")
    private String respCode;
    @TableField("SYS_TIME")
    private String sysTime;
    @TableField("AMOUNT")
    private Double amount;
    @TableField("TOTAL_AMOUNT")
    private Double totalAmount;
    @TableField("AUTH_CODE")
    private String authCode;
    @TableField("PAY_CHANNEL")
    private String payChannel;
    @TableField("GOODS_TAG")
    private String goodsTag;
    @TableField("BODYDETAIL")
    private String bodydetail;
    @TableField("GOODS_DETAIL")
    private String goodsDetail;
    @TableField("ATTACH")
    private String attach;
    @TableField("NEED_RECEIPT")
    private String needReceipt;
    @TableField("OUT_TRANSACTION_ID")
    private String outTransactionId;
    @TableField("SUB_IS_SUBSCRIBE")
    private String subIsSubscribe;
    @TableField("SUB_APPID")
    private String subAppid;
    @TableField("INVOICE_AMOUNT")
    private Double invoiceAmount;
    @TableField("TIME_END")
    private Date timeEnd;
    @TableField("BANK_BILLNO")
    private String bankBillno;
    @TableField("SUB_TRANS_TYPE")
    private String subTransType;
    @TableField("IS_SUBSCRIBE")
    private String isSubscribe;
    @TableField("POINT_AMOUNT")
    private Double pointAmount;
    @TableField("COUPON_FEE")
    private Double couponFee;
    @TableField("PROMOTION_DETAIL")
    private String promotionDetail;
    @TableField("DISCOUNT_GOODS_DETAIL")
    private String discountGoodsDetail;
    @TableField("FUND_BILL_LIST")
    private String fundBillList;
    @TableField("RECEIPT_AMOUNT")
    private Double receiptAmount;
    @TableField("BUYER_PAY_AMOUNT")
    private Double buyerPayAmount;
    @TableField("BANK_TYPE")
    private String bankType;
    @TableField("UUID")
    private String uuid;
    @TableField("MDISCOUNT")
    private Double mdiscount;
    @TableField("UNIONPAY_DISCOUNT")
    private String unionpayDiscount;
    @TableField("GMT_PAYMENT")
    private Date gmtPayment;
    @TableField("BUYER_USER_ID")
    private String buyerUserId;
    @TableField("BUYER_LOGON_ID")
    private String buyerLogonId;
    @TableField("APPID")
    private String appid;
    @TableField("OPENID")
    private String openid;
    @TableField("SUB_OPENID")
    private String subOpenid;
    @TableField("REFUND_COUNT")
    private Long refundCount;
    @TableField("REFUND_STATUS")
    private String refundStatus;
    @TableField("REFUND_CHANNEL")
    private String refundChannel;
    @TableField("REFUND_FEE")
    private Double refundFee;
    @TableField("COUPON_REFUND_FEE")
    private Double couponRefundFee;
    @TableField("TXN_CNL")
    private String txnCnl;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
