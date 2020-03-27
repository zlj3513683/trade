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
 *  星终端信息产品表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_TPRD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmTprd extends Model<UrmTprd> {

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
     * 产品类型
     */
    @TableField("PRD_ID")
    private String prdId;
    /**
     * 平台终端号
     */
    @TableField("TRM_NO")
    private String trmNo;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 设备形态
     */
    @TableField("EQM_TYPE")
    private String eqmType;
    /**
     * 渠道终端号
     */
    @TableField("BANK_TRM_NO")
    private String bankTrmNo;


    @Override
    protected Serializable pkVal() {
        return this.stoeId;
    }

}
