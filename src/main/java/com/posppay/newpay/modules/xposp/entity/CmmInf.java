package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 机构签到信息
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CMM_INF")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmmInf extends Model<CmmInf> {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道号
     */
    @TableField("CORG_NO")
    private String corgNo;
    /**
     * 渠道商户号
     */
    @TableField("CORG_MERC_ID")
    private String corgMercId;
    /**
     * 渠道终端号
     */
    @TableField("CORG_TRM_NO")
    private String corgTrmNo;
    @TableField("CORG_MERC_NM")
    private String corgMercNm;
    @TableField("CORG_BAT_NO")
    private String corgBatNo;
    @TableField("CORG_TRM_KEY")
    private String corgTrmKey;
    /**
     * 渠道pin秘钥
     */
    @TableField("CORG_PIN_KEY")
    private String corgPinKey;
    /**
     * 渠道mac秘钥
     */
    @TableField("CORG_MAC_KEY")
    private String corgMacKey;
    @TableField("CORG_SIG_DT")
    private String corgSigDt;
    @TableField("CORG_TRM_CNT")
    private Double corgTrmCnt;
    @TableField("RMK")
    private String rmk;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    @TableField("CORG_MASTER_KEY")
    private String corgMasterKey;
    @TableField("CORG_PROV_CD")
    private String corgProvCd;
    @TableField("MAX_FLG")
    private String maxFlg;
    @TableField("SEND_NUM")
    private String sendNum;
    @TableField("CORG_KEY")
    private String corgKey;
    @TableField("CORG_SIG_STS")
    private String corgSigSts;
    @TableField("USE_FLG")
    private String useFlg;
    @TableField("CORG_TRM_SN")
    private String corgTrmSn;
    @TableField("CITY_CD")
    private String cityCd;
    @TableField("PROV_CD")
    private String provCd;
    @TableField("DLY_MAX_AMT")
    private String dlyMaxAmt;
    @TableField("CORG_TRACK_KEY")
    private String corgTrackKey;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}

