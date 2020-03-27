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
 * 公共属性表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_COMI")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmComi extends Model<UrmComi> {

    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @TableId("MERC_ID")
    private String mercId;
    /**
     * 内部用户号
     */
    @TableField("USR_NO")
    private String usrNo;
    @TableField("PSN_CRP_FLG")
    private String psnCrpFlg;
    @TableField("BUY_SAL_FLG")
    private String buySalFlg;
    @TableField("USR_STS")
    private String usrSts;
    @TableField("SCU_LVL")
    private String scuLvl;
    @TableField("KEY_TYP")
    private String keyTyp;
    /**
     * md5秘钥
     */
    @TableField("SIGN_KEY")
    private String signKey;
    @TableField("ECP_KEY")
    private String ecpKey;
    /**
     * 验签方式
     */
    @TableField("ECP_ALG")
    private String ecpAlg;
    @TableField("USR_DISP_NM")
    private String usrDispNm;
    @TableField("CI_NO")
    private String ciNo;
    @TableField("MAIN_AC")
    private String mainAc;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;


    @Override
    protected Serializable pkVal() {
        return this.mercId;
    }

}
