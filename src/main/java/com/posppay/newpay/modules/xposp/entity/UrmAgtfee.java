package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_AGTFEE")
public class UrmAgtfee extends Model<UrmAgtfee> {

    private static final long serialVersionUID = 1L;

    @TableField("AGT_MERC_ID")
    private String agtMercId;
    @TableField("MERC_ID")
    private String mercId;
    @TableField("CAP_TYP")
    private String capTyp;
    @TableField("TX_MOD")
    private String txMod;
    @TableField("FEE_TYP")
    private String feeTyp;
    @TableField("MCC_CD")
    private String mccCd;
    @TableField("CRD_IND")
    private String crdInd;
    @TableField("EFF_DT")
    private String effDt;
    @TableField("EXP_DT")
    private String expDt;
    @TableField("FEE_DRT_FLG")
    private String feeDrtFlg;
    @TableField("FEE_PERD_UNIT")
    private String feePerdUnit;
    @TableField("FEE_PERD_NUM")
    private Integer feePerdNum;
    @TableField("NEXT_FEE_DT")
    private String nextFeeDt;
    @TableField("MERC_FEE_MOD")
    private String mercFeeMod;
    @TableField("FEE_CAL_MTH")
    private String feeCalMth;
    @TableField("FEE_CCY")
    private String feeCcy;
    @TableField("BEG_AMT")
    private Double begAmt;
    @TableField("MIN_FEE_AMT")
    private Double minFeeAmt;
    @TableField("MAX_FEE_AMT")
    private Double maxFeeAmt;
    @TableField("FEE_CAL_BAS_FLG")
    private String feeCalBasFlg;
    @TableField("FEE_LVL_FLG")
    private String feeLvlFlg;
    @TableField("FEE_LVL_BAS_FLG")
    private String feeLvlBasFlg;
    @TableField("UP_REF1")
    private Double upRef1;
    @TableField("FIX_FEE_AMT1")
    private Double fixFeeAmt1;
    @TableField("FEE_RAT1")
    private Double feeRat1;
    @TableField("UP_REF2")
    private Double upRef2;
    @TableField("FIX_FEE_AMT2")
    private Double fixFeeAmt2;
    @TableField("FEE_RAT2")
    private Double feeRat2;
    @TableField("UP_REF3")
    private Double upRef3;
    @TableField("FIX_FEE_AMT3")
    private Double fixFeeAmt3;
    @TableField("FEE_RAT3")
    private Double feeRat3;
    @TableField("UP_REF4")
    private Double upRef4;
    @TableField("FIX_FEE_AMT4")
    private Double fixFeeAmt4;
    @TableField("FEE_RAT4")
    private Double feeRat4;
    @TableField("UP_REF5")
    private Double upRef5;
    @TableField("FIX_FEE_AMT5")
    private Double fixFeeAmt5;
    @TableField("FEE_RAT5")
    private Double feeRat5;
    @TableField("UPD_JRN")
    private Long updJrn;
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 微信机构费率
     */
    @TableField("FEE_WX")
    private BigDecimal feeWx;
    /**
     * 支付宝机构费率
     */
    @TableField("FEE_ALI")
    private BigDecimal feeAli;
    /**
     * 贷记卡小于1000元费率
     */
    @TableField("FEE_UNION_LOAN_FAVOUR")
    private BigDecimal feeUnionLoanFavour;
    /**
     * 贷记卡大于1000元费率
     */
    @TableField("FEE_UNION_LOAN_NORMAL")
    private BigDecimal feeUnionLoanNormal;
    /**
     * 借记卡大于1000元费率
     */
    @TableField("FEE_UNION_BORROW_NORMAL")
    private BigDecimal feeUnionBorrowNormal;
    /**
     * 借记卡小于1000元费率
     */
    @TableField("FEE_UNION_BORROW_FAVOUR")
    private BigDecimal feeUnionBorrowFavour;
    /**
     * 银联封顶费率
     */
    @TableField("FEE_UNION_BORROW_NORMAL_MAX")
    private BigDecimal feeUnionBorrowNormalMax;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
