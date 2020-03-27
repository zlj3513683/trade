package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 银联地区码
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CUP_AREA")
public class CupArea extends Model<CupArea> {

    private static final long serialVersionUID = 1L;

    /**
     * 银联区域ID
     */
    @TableId("CUP_AREA_ID")
    private String cupAreaId;
    /**
     * 银联区域名
     */
    @TableField("CUP_AREA_NAME")
    private String cupAreaName;
    /**
     * 银联区域父ID
     */
    @TableField("CUP_AREA_PARENT_ID")
    private String cupAreaParentId;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 星POS区域编码
     */
    @TableField("STARPOS_CODE")
    private String starposCode;
    /**
     * 平安区域编码
     */
    @TableField("PINGAN_CODE")
    private String pinganCode;


    @Override
    protected Serializable pkVal() {
        return this.cupAreaId;
    }

}
