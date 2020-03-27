package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 结算信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_MSTL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmMstl extends Model<UrmMstl> {

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
     * 暂停结算标志,0：正常  1：暂停
     */
    @TableField("PAS_STL_FLG")
    private String pasStlFlg;
    /**
     * 结算账户标志,0：对公  1：对私 ,对私——银联借记卡，对公——非银联借记卡
     */
    @TableField("STL_SIGN")
    private String stlSign;
    /**
     * 结算类型,0：T+0 1：T+1
     */
    @TableField("STL_TYP")
    private String stlTyp;
    /**
     * 结算账号
     */
    @TableField("STL_OAC")
    private String stlOac;
    /**
     * 结算账户类型,1：存折 2：银联借记卡 3：非银联借记卡
     */
    @TableField("STL_OAC_CLS")
    private String stlOacCls;
    /**
     * 户名
     */
    @TableField("BNK_ACNM")
    private String bnkAcnm;
    /**
     * 结算人身份证号
     */
    @TableField("STL_CRP_NO")
    private String stlCrpNo;
    /**
     * 身份证号到期日期
     */
    @TableField("CRP_END_DT")
    private String crpEndDt;
    /**
     * 联行行号
     */
    @TableField("WC_LBNK_NO")
    private String wcLbnkNo;
    /**
     * 开户行所在省
     */
    @TableField("OPN_BNK_PROV")
    private String opnBnkProv;
    /**
     * 开户行所在市
     */
    @TableField("OPN_BNK_CITY")
    private String opnBnkCity;
    /**
     * 结算银名称
     */
    @TableField("OPN_BNK_DESC")
    private String opnBnkDesc;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 结算卡正面照
     */
    @TableField("MET_IMG")
    private String metImg;
    /**
     * 结算手持身份证照
     */
    @TableField("HOLD_IMG")
    private String holdImg;
    /**
     * 支行名称
     */
    @TableField("OPN_BNK_SUB")
    private String opnBnkSub;
    /**
     * 结算人反面照
     */
    @TableField("MERC_BANKCODE_IMG")
    private String mercBankcodeImg;
    /**
     * 结算人正面照
     */
    @TableField("MERC_OPENBANK_IMG")
    private String mercOpenbankImg;
    /**
     * 是否开通D0 0开通 1关闭
     */
    @TableField("STL_TYP2")
    private String stlTyp2;
    /**
     * 原始银行卡号
     */
    @TableField("ORIGINAL_STL_OAC")
    private String originalStlOac;


    @Override
    protected Serializable pkVal() {
        return this.mercId;
    }

}
