package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 费率表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_MFEE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmMfee extends Model<UrmMfee> {

    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @TableId("MERC_ID")
    private String mercId;
    /**
     * 门店号
     */
    @TableField("STOE_ID")
    private String stoeId;
    /**
     * 产品编号
     */
    @TableField("PRD_ID")
    private String prdId;
    /**
     * 费率
     */
    @TableField("FEE_RAT")
    private BigDecimal feeRat;
    /**
     * 费率1
     */
    @TableField("FEE_RAT1")
    private BigDecimal feeRat1;
    /**
     * 封顶费率
     */
    @TableField("MAX_FEE_AMT")
    private BigDecimal maxFeeAmt;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 费率2
     */
    @TableField("FEE_RAT2")
    private BigDecimal feeRat2;
    @TableField("MSHD0_FEE_RAT")
    private BigDecimal mshd0FeeRat;
    @TableField("SERVICE_FEE")
    private String serviceFee;
    @TableField("PAY_DAY_MAX")
    private String payDayMax;
    @TableField("PAY_DAY_MIN")
    private String payDayMin;
    @TableField("PAY_SIG_MAX")
    private String paySigMax;
    @TableField("SERVICE_FEE1")
    private String serviceFee1;
    @TableField("FM_FLG")
    private String fmFlg;
    @TableField("FM_AMT")
    private BigDecimal fmAmt;
    @TableField("FEE_RAT3")
    private BigDecimal feeRat3;
    @TableField("SPEFEEALI")
    private BigDecimal spefeeali;
    @TableField("SPEFEEALIAMT")
    private BigDecimal spefeealiamt;
    @TableField("SPEFEEALIFLG")
    private String spefeealiflg;
    @TableField("SPEFEEWX")
    private BigDecimal spefeewx;
    @TableField("SPEFEEWXAMT")
    private BigDecimal spefeewxamt;
    @TableField("SPEFEEWXFLG")
    private String spefeewxflg;
    @TableField("YSFDEBITFEE")
    private BigDecimal ysfdebitfee;
    @TableField("YSFCREDITFEE")
    private BigDecimal ysfcreditfee;
    /**
     * 固定书续费
     */
    @TableField("FIX_FEE")
    private String fixFee;
    @TableField("LVZH_FEE")
    private String lvzhFee;
    @TableField("BANK_RESERVED_PHONE")
    private String bankReservedPhone;
    /**
     * 支付宝扫码支付费率
     */
    @TableField("FEE_ALI_SCAN")
    private BigDecimal feeAliScan;
    /**
     * 支付宝小额支付费率
     */
    @TableField("FEE_ALI_CODE")
    private BigDecimal feeAliCode;
    /**
     * 支付宝js支付费率
     */
    @TableField("FEE_ALI_TABLE")
    private BigDecimal feeAliTable;
    /**
     * 微信扫码支付费率
     */
    @TableField("FEE_WX_SCAN")
    private BigDecimal feeWxScan;
    /**
     * 微信小额支付费率
     */
    @TableField("FEE_WX_CODE")
    private BigDecimal feeWxCode;
    /**
     * 微信公众号支付费率
     */
    @TableField("FEE_WX_TABLE")
    private BigDecimal feeWxTable;
    /**
     * 银联扫码支付借记卡大于1000费率
     */
    @TableField("FEE_UNION_SCAN_BORROW_NORMAL")
    private BigDecimal feeUnionScanBorrowNormal;
    /**
     * 银联扫码支付贷记卡大于1000费率
     */
    @TableField("FEE_UNION_SCAN_LOAN_NORMAL")
    private BigDecimal feeUnionScanLoanNormal;
    /**
     * 银联小额支付借记卡大于1000费率
     */
    @TableField("FEE_UNION_CODE_BORROW_NORMAL")
    private BigDecimal feeUnionCodeBorrowNormal;
    /**
     * 银联小额支付贷记卡大于1000费率
     */
    @TableField("FEE_UNION_CODE_LOAN_NORMAL")
    private BigDecimal feeUnionCodeLoanNormal;
    /**
     * 银联扫码支付借记卡小于或等于1000费率
     */
    @TableField("FEE_UNION_SCAN_BORROW_FAVOUR")
    private BigDecimal feeUnionScanBorrowFavour;
    /**
     * 银联扫码支付贷记卡小于或等于1000费率
     */
    @TableField("FEE_UNION_SCAN_LOAN_FAVOUR")
    private BigDecimal feeUnionScanLoanFavour;
    /**
     * 银联小额支付借记卡小于或等于1000费率
     */
    @TableField("FEE_UNION_CODE_BORROW_FAVOUR")
    private BigDecimal feeUnionCodeBorrowFavour;
    /**
     * 银联小额支付贷记卡小于或等于1000费率
     */
    @TableField("FEE_UNION_CODE_LOAN_FAVOUR")
    private BigDecimal feeUnionCodeLoanFavour;
    /**
     * 银联小额支付贷记卡小于或等于1000费率
     */
    @TableField("FEE_UNION_SCAN_BORROW_NORMAL_MAX")
    private BigDecimal feeUnionScanBorrowNormalMax;


    @Override
    protected Serializable pkVal() {
        return this.mercId;
    }

}
