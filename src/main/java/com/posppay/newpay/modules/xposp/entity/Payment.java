package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 平台流水表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_PAYMENT")
public class Payment extends Model<Payment> {

    private static final long serialVersionUID = 1L;

    /**
     * 平台流水表主键
     */
    @TableId("PAY_ID")
    private String payId;
    /**
     * 机构号
     */
    @TableField("ORG_NO")
    private String orgNo;
    /**
     * 交易渠道编码
     */
    @TableField("CHN_CODE")
    private String chnCode;
    /**
     * 系统交易流水号
     */
    @TableField("PAY_NO")
    private String payNo;
    /**
     * 订单号
     */
    @TableField("ORD_NO")
    private String ordNo;
    /**
     * 商户号
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
     * 交易金额
     */
    @TableField("TRANS_AMOUNT")
    private BigDecimal transAmount;
    /**
     * 交易类型
     */
    @TableField("TRANS_TYPE")
    private String transType;
    /**
     * 交易状态
     */
    @TableField("TRANS_STATUS")
    private String transStatus;
    /**
     * 交易结果码
     */
    @TableField("TRANS_CODE")
    private String transCode;
    /**
     * 交易结果消息
     */
    @TableField("TRANS_MSG")
    private String transMsg;
    /**
     * 原始平台流水号
     */
    @TableField("ORIGIN_PAYNO")
    private String originPayno;
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
     * 交易子类型
     */
    @TableField("SUB_TRANS_TYPE")
    private String subTransType;
    /**
     * 渠道订单号
     */
    @TableField("CHN_ORD_NO")
    private String chnOrdNo;
    /**
     * 系统门店号
     */
    @TableField("STORE_NO")
    private String storeNo;
    /**
     * 门店名
     */
    @TableField("STORE_NAME")
    private String storeName;
    /**
     * 交易时间
     */
    @TableField("TRANS_TIME")
    private Date transTime;
    /**
     * 商户费率%
     */
    @TableField("MRCH_RATES")
    private BigDecimal mrchRates;
    /**
     * 商户费率金额
     */
    @TableField("MRCH_RATES_AMOUNT")
    private BigDecimal mrchRatesAmount;
    /**
     * 设备SN
     */
    @TableField("DEV_SN")
    private String devSn;
    /**
     * 下游商户系统的凭证号
     */
    @TableField("OUT_ORDER_NO")
    private String outOrderNo;

    /**
     * 第三方订单号
     * @return
     */
    @TableField("OUT_TRANSACTION_ID")
    private String outTransactionId;

    /**
     * ip地址
     * @return
     */
    @TableField("IP_ADDRESS")
    private String ipAndAddress;
    /**
     * 商品描述
     */
    @TableField("OUT_BODY")
    private String outBody;
    /**
     * 付款类型
     */
    @TableField("PAY_CHANNEL")
    private String payChannel;
    /**
     * 机构费率
     */
    @TableField("ORG_FEE")
    private BigDecimal orgFee;
    /**
     * 通道费率
     */
    @TableField("CHN_FEE")
    private BigDecimal chnFee;
    /**
     * 机构费率金额
     */
    @TableField("ORG_FEE_AMT")
    private BigDecimal orgFeeAmt;
    /**
     * 通道费率金额
     */
    @TableField("CHN_FEE_AMT")
    private BigDecimal chnFeeAmt;
    /**
     * 银行类型
     */
    @TableField("BANK_TYPE")
    private String bankType;

    /**
     * 商户附加信息
     */
    @TableField("ATTACH")
    private String attach;
    /**
     * 二维码字符串
     */
    @TableField(exist = false)
    private String codeUrl;
    /**
     * 二维码图片
     */
    @TableField(exist = false)
    private String codeImg;

    @TableField(exist = false)
    private String tokenId;
    /**
     * 二维码图片
     */
    @TableField(exist = false)
    private String payInfo;
    /**
     * 用户openid
     */
    @TableField("OPENID")
    private String openid;
    /**
     * 商户appid
     */
    @TableField("SUB_APPID")
    private String subAppid;
    /**
     * 商品标记
     */
    @TableField("GOODS_TAG")
    private String goodsTag;
    /**
     * 商品详情
     */
    @TableField("GOODS_DETAIL")
    private String goodsDetail;
    @TableField("OUT_TRANSACTION_ID")
    private String outTranstionId;


    @Override
    protected Serializable pkVal() {
        return this.payId;
    }

}
