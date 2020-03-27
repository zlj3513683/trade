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
 * 商户信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_MINF")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmMinf extends Model<UrmMinf> {

    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @TableId("MERC_ID")
    private String mercId;
    /**
     * 商户名称
     */
    @TableField("MERC_NM")
    private String mercNm;
    /**
     * 商户注册名
     */
    @TableField("MERC_CNM")
    private String mercCnm;
    /**
     * 商户状态
     */
    @TableField("MERC_STS")
    private String mercSts;
    /**
     * 省份编码
     */
    @TableField("MERC_PROV")
    private String mercProv;
    /**
     * 城市编码
     */
    @TableField("MERC_CITY")
    private String mercCity;
    /**
     * 商户地址
     */
    @TableField("MERC_ADDS")
    private String mercAdds;
    /**
     * 内部用户号
     */
    @TableField("USR_NO")
    private String usrNo;
    @TableField("TAX_REG_NO")
    private String taxRegNo;
    @TableField("ORG_COD")
    private String orgCod;
    /**
     * 营业执照号
     */
    @TableField("BUS_LIC_NO")
    private String busLicNo;
    @TableField("MGT_SCP")
    private String mgtScp;
    @TableField("MCC_SUB_CD")
    private String mccSubCd;
    /**
     * 营业执照照片
     */
    @TableField("BUS_LIC_IMG")
    private String busLicImg;
    /**
     * 法人名称
     */
    @TableField("CRP_NM")
    private String crpNm;
    /**
     * 证件类型
     */
    @TableField("CRP_ID_TYP")
    private String crpIdTyp;
    /**
     * 证件号
     */
    @TableField("CRP_ID_NO")
    private String crpIdNo;
    /**
     * 电话号码
     */
    @TableField("CRP_PHONE")
    private String crpPhone;
    /**
     * 法人证件正面照
     */
    @TableField("CRP_CS_IMG")
    private String crpCsImg;
    /**
     * 法人证件反面照
     */
    @TableField("CRP_OS_IMG")
    private String crpOsImg;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 进件时间
     */
    @TableField("SIG_DT")
    private String sigDt;
    /**
     * 新增的操作员
     */
    @TableField("ADD_OPER")
    private String addOper;
    /**
     * 修改的操作员
     */
    @TableField("UPD_OPER")
    private String updOper;
    /**
     * 更新的时间
     */
    @TableField("UPD_TM")
    private String updTm;
    /**
     * 商户地区编号
     */
    @TableField("MERC_AREA_COD")
    private String mercAreaCod;
    /**
     * 营业执照到期日
     */
    @TableField("BUS_EXP_DT")
    private String busExpDt;
    /**
     * 法人身份证到期日
     */
    @TableField("CRP_EXP_DT")
    private String crpExpDt;
    @TableField("MER_RES_IMG")
    private String merResImg;
    /**
     * 商户等级
     */
    @TableField("CHK_CRE_RAT")
    private String chkCreRat;
    @TableField("BUSINE_RST")
    private String busineRst;
    @TableField("THR_ELE_STS")
    private String thrEleSts;
    @TableField("IS_CRP_MERC")
    private String isCrpMerc;
    @TableField("IN_OCC_ATTR")
    private String inOccAttr;
    @TableField("AUTO_CHK_FLAG")
    private String autoChkFlag;
    /**
     * 商户归属机构
     */
    @TableField("MERC_OWN_ORG")
    private String mercOwnOrg;
    @TableField("REVIEW_PASS_DT")
    private String reviewPassDt;
    @TableField("ADD_REVIEW_OPER")
    private String addReviewOper;
    @TableField("UPD_REVIEW_OPER")
    private String updReviewOper;
    @TableField("TWO_ELE_STS")
    private String twoEleSts;
    @TableField("MERC_OGCC_IMG")
    private String mercOgccImg;
    @TableField("MERC_CHANNEL")
    private String mercChannel;
    @TableField("CONTRACT_NO")
    private String contractNo;
    @TableField("KEY")
    private String key;
    @TableField("REVIEW_PASS_FIRSTM")
    private String reviewPassFirstm;
    /**
     * 补充资料图片1
     */
    @TableField("MER_CON1_IMG")
    private String merCon1Img;
    /**
     * 补充资料图片2
     */
    @TableField("MER_CON2_IMG")
    private String merCon2Img;
    /**
     * 补充资料图片3
     */
    @TableField("MER_CON3_IMG")
    private String merCon3Img;
    /**
     * 补充资料图片4
     */
    @TableField("MER_CON4_IMG")
    private String merCon4Img;
    @TableField("INT_COD")
    private String intCod;
    @TableField("MERC498_STATS")
    private String merc498Stats;
    @TableField("BAT_PIC_FLG")
    private String batPicFlg;
    @TableField("MERC_STS_TMP")
    private String mercStsTmp;
    @TableField("MERC_ADDSTATS")
    private String mercAddstats;
    @TableField("PBANK_MERCID")
    private String pbankMercid;
    @TableField("PBANK_ERRORMESS")
    private String pbankErrormess;
    @TableField("MERC_TYPE")
    private String mercType;
    @TableField("SIG_PANK_CODE")
    private String sigPankCode;
    @TableField("SPECIAL_FLG")
    private String specialFlg;
    /**
     * 进件类型
     */
    @TableField("INCOM_TYPE")
    private String incomType;
    /**
     * 平台协议图片存储地址
     */
    @TableField("PLATFORM_PROTOCOL_PATH")
    private String platformProtocolPath;


    @Override
    protected Serializable pkVal() {
        return this.mercId;
    }

}
