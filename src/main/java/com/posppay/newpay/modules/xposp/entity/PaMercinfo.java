package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 平安商户信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_PA_MERCINFO")
public class PaMercinfo extends Model<PaMercinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id号
     */
    @TableId("PA_ID")
    private String paId;
    /**
     * 平台商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 平安商户号
     */
    @TableField("PA_MERC_ID")
    private String paMercId;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 下游商户号
     */
    @TableField("DOWNSTREAM_MERCHANT")
    private String downstreamMerchant;
    @TableField("UPD_TMP")
    private String updTmp;


    @Override
    protected Serializable pkVal() {
        return this.paId;
    }

}
