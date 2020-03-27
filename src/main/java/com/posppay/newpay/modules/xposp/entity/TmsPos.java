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
 * 终端信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_TMS_POS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmsPos extends Model<TmsPos> {

    private static final long serialVersionUID = 1L;

    /**
     * 门店号
     */
    @TableId("STOE_ID")
    private String stoeId;
    /**
     * 商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 平台终端号
     */
    @TableField("TRM_NO")
    private String trmNo;
    /**
     * csn号
     */
    @TableField("SN_NO")
    private String snNo;
    /**
     * 终端状态
     */
    @TableField("TRM_STS")
    private String trmSts;
    /**
     * 签到状态
     */
    @TableField("SGN_STS")
    private String sgnSts;
    /**
     * 公钥更新标志
     */
    @TableField("PM_DW_FLG")
    private String pmDwFlg;
    /**
     * 参数更新标志
     */
    @TableField("EMV_DW_FLG")
    private String emvDwFlg;
    /**
     * 银联地区码
     */
    @TableField("RARD_CD")
    private String rardCd;
    /**
     * 批次号
     */
    @TableField("BAT_NO")
    private String batNo;
    /**
     * 签到日期
     */
    @TableField("SIG_DT")
    private String sigDt;
    /**
     * 结算标志
     */
    @TableField("BAT_CHK_FLG")
    private String batChkFlg;
    /**
     * 激活码
     */
    @TableField("POS_KEY")
    private String posKey;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    @TableField("TRM_TEK")
    private String trmTek;
    @TableField("TRM_ZMK")
    private String trmZmk;
    /**
     * 是否被绑定
     */
    @TableField("BIND_STS")
    private String bindSts;
    @TableField("SN_NO1")
    private String snNo1;
    @TableField("OWN_ORG_NO")
    private String ownOrgNo;
    @TableField("TMK_CK_VALUE")
    private String tmkCkValue;
    @TableField("CSN_NO")
    private String csnNo;
    /**
     * 绑定次数
     */
    @TableField("TYING_NUM")
    private String tyingNum;
    /**
     * 设备形态
     */
    @TableField("EQM_TYPE")
    private String eqmType;
    @TableField("SUPT_DBFREE_FLG")
    private String suptDbfreeFlg;
    /**
     * 免密限额
     */
    @TableField("NO_PSWD_LIMIT")
    private Double noPswdLimit;
    /**
     * 免签限额
     */
    @TableField("NO_SIGN_LIMIT")
    private Double noSignLimit;
    /**
     * 绑定日期
     */
    @TableField("BIND_DT")
    private String bindDt;
    @TableField("SN_NO2")
    private String snNo2;
    @TableField("MONEY_MORE_FLG")
    private String moneyMoreFlg;
    @TableField("SERVICE_CHARGE_MODE")
    private String serviceChargeMode;
    @TableField("SERVICE_REMARKS")
    private String serviceRemarks;
    @TableField("BIN_FLG")
    private String binFlg;
    @TableField("EQMT_TYPE")
    private String eqmtType;
    @TableField("XNTRM_NUM")
    private String xntrmNum;


    @Override
    protected Serializable pkVal() {
        return this.stoeId;
    }

}
