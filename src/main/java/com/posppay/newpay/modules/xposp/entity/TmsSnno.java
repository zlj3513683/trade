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
 * 终端秘钥信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_TMS_SNNO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmsSnno extends Model<TmsSnno> {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @TableField("AC_DT")
    private String acDt;
    /**
     * 终端平台推送的csn号
     */
    @TableId("SN_NO")
    private String snNo;
    @TableField("TRM_KEY")
    private String trmKey;
    /**
     * 磁道秘钥
     */
    @TableField("TRM_TEK")
    private String trmTek;
    /**
     * mac秘钥
     */
    @TableField("MAC_KEY")
    private String macKey;
    /**
     * pin秘钥
     */
    @TableField("PIN_KEY")
    private String pinKey;
    @TableField("SN_STS")
    private String snSts;
    @TableField("TM_SMP")
    private String tmSmp;
    @TableField("TEK")
    private String tek;
    @TableField("TEK_STS")
    private String tekSts;
    @TableField("TEK_SIGN")
    private String tekSign;
    @TableField("TEK2")
    private String tek2;
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
    /**
     * 是否已经绑定
     */
    @TableField("BIND_STS")
    private String bindSts;
    /**
     * 台牌交易url
     */
    @TableField("CODE_URL")
    private String codeUrl;
    /**
     * 台牌二维码地址（绝对路径）
     */
    @TableField("CODE_PATH")
    private String codePath;
    /**
     * 最后一次下载日期
     */
    @TableField("DOWN_DT")
    private String downDt;
    /**
     * 当前用户
     */
    @TableField("USR_NAME")
    private String usrName;
    @TableField("BAT_NO")
    private String batNo;
    @TableField("YLQRCODE")
    private String ylqrcode;
    @TableField("YLQRSTATUS")
    private String ylqrstatus;
    @TableField("YLBINDCODE")
    private String ylbindcode;
    @TableField("SN_NOCODE")
    private String snNocode;
    @TableField("BUS_TYP")
    private String busTyp;
    @TableField("BUS_TYP_NAME")
    private String busTypName;


    @Override
    protected Serializable pkVal() {
        return this.snNo;
    }

}
