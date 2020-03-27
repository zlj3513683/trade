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
 * 星pos商户关联表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_MMAP")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmMmap extends Model<UrmMmap> {

    private static final long serialVersionUID = 1L;

    /**
     * 门店号
     */
    @TableId("STOE_ID")
    private String stoeId;
    /**
     * 产品编号
     */
    @TableField("PRD_ID")
    private String prdId;
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
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 平台商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    @TableField("MSHALI_MERC_ID")
    private String mshaliMercId;
    @TableField("MSHWX_MERC_ID")
    private String mshwxMercId;
    @TableField("MSHALI_STATE")
    private String mshaliState;
    @TableField("MSHWX_STATE")
    private String mshwxState;
    @TableField("MSH_SUCC_TM")
    private String mshSuccTm;
    @TableField("OPEN_ACCOUNT_STATS")
    private String openAccountStats;
    @TableField("YL_MERC")
    private String ylMerc;
    @TableField("VOU_STATS")
    private String vouStats;
    /**
     * 星pos秘钥
     */
    @TableField("STAR_KEY")
    private String starKey;
    /**
     * 星pos门店号
     */
    @TableField("STAR_STORE_ID")
    private String starStoreId;


    @Override
    protected Serializable pkVal() {
        return this.stoeId;
    }

}
