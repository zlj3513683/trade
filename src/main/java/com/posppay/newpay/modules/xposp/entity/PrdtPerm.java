package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 产品权限表
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_PRDT_PERM")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrdtPerm extends Model<PrdtPerm> {

    private static final long serialVersionUID = 1L;

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
     * 交易码
     */
    @TableField("TRAN_TYP")
    private String tranTyp;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 门店号
     */
    @TableField("STOE_ID")
    private String stoeId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
