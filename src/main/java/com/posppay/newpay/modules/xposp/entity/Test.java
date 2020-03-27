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
 *
 * </p>
 *
 * @author zengjw
 * @since 2019-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_TEST")
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

    @TableField("MERCHANT_ID")
    private Long merchantId;
    @TableField("ORGANIZATION_ID")
    private Long organizationId;
    @TableField("MERCHANT_NAME")
    private String merchantName;
    @TableField("MERCHANT_SHORT_NAME")
    private String merchantShortName;
    @TableField("MERCHANT_TYPE")
    private String merchantType;
    @TableField("INDUSTRY_CATEGORY")
    private String industryCategory;
    @TableId("ID)")
    private double id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
