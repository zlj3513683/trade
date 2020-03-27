package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("T_URM_AGNT")
public class UrmAgnt extends Model<UrmAgnt> {

    private static final long serialVersionUID = 1L;

    @TableField("AGT_MERC_ID")
    private String agtMercId;
    @TableField("USR_NO")
    private Long usrNo;
    @TableField("AC_NO")
    private String acNo;
    @TableField("AC_TYP")
    private String acTyp;
    @TableField("AGT_MERC_PROV")
    private String agtMercProv;
    @TableField("AGT_MERC_CITY")
    private String agtMercCity;
    @TableField("AGT_MERC_CITY_NM")
    private String agtMercCityNm;
    @TableField("ORG_NO")
    private String orgNo;
    @TableField("AGT_MERC_NM")
    private String agtMercNm;
    @TableField("MERC_TYP")
    private String mercTyp;
    @TableField("MERC_CNM")
    private String mercCnm;
    @TableField("MERC_ABBR")
    private String mercAbbr;
    @TableField("MERC_ENM")
    private String mercEnm;
    @TableField("REG_ORG_NM")
    private String regOrgNm;
    @TableField("AGT_MERC_ATTR")
    private String agtMercAttr;
    @TableField("TAX_REG_NO")
    private String taxRegNo;
    @TableField("REG_ID")
    private String regId;
    @TableField("ORG_COD")
    private String orgCod;
    @TableField("REG_ADDR")
    private String regAddr;
    @TableField("CRP_NM")
    private String crpNm;
    @TableField("CRP_ID_TYP")
    private String crpIdTyp;
    @TableField("CRP_ID_NO")
    private String crpIdNo;
    @TableField("REG_CAP_AMT")
    private Double regCapAmt;
    @TableField("MGT_SECT")
    private String mgtSect;
    @TableField("MGT_RGN")
    private String mgtRgn;
    @TableField("BUS_ADDR")
    private String busAddr;
    @TableField("BUS_ZIP")
    private String busZip;
    @TableField("FAX")
    private String fax;
    @TableField("STAF_NUM")
    private Integer stafNum;
    @TableField("MERC_CHK_ADDR")
    private String mercChkAddr;
    @TableField("MERC_CHK_ZIP")
    private String mercChkZip;
    @TableField("MERC_CHK_COMM")
    private String mercChkComm;
    @TableField("MERC_CHK_EMAIL")
    private String mercChkEmail;
    @TableField("MERC_CHK_FAX")
    private String mercChkFax;
    @TableField("AGT_MERC_LVL")
    private String agtMercLvl;
    @TableField("CRE_REF_NO")
    private String creRefNo;
    @TableField("EFF_DT")
    private String effDt;
    @TableField("EXP_DT")
    private String expDt;
    @TableField("CUS_MGR")
    private String cusMgr;
    @TableField("CUS_MGR_NM")
    private String cusMgrNm;
    @TableField("UP_AGT_MERC_ID")
    private String upAgtMercId;
    @TableField("AGT_MERC_STS")
    private String agtMercSts;
    @TableField("TM_SMP")
    private String tmSmp;
    @TableField("AGT_BOND_AMT")
    private Double agtBondAmt;
    @TableField("FES_FEQ")
    private String fesFeq;
    @TableField("AGT_UPD_OPER")
    private String agtUpdOper;
    @TableField("AGN_APPR_RMK")
    private String agnApprRmk;
    @TableField("AGN_RVW_OPR")
    private String agnRvwOpr;
    @TableField("AGN_RVW_DT")
    private String agnRvwDt;
    @TableField("AGT_STS")
    private String agtSts;
    @TableField("AGT_REC_CD")
    private String agtRecCd;
    @TableField("SCR_CHANGE_FLG")
    private String scrChangeFlg;
    @TableField("ZIP_FIL_PATH")
    private String zipFilPath;
    @TableField("MOBILE_PHONE")
    private Long mobilePhone;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
