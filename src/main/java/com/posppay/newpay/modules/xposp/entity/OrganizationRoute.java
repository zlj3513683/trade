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
 * 平安机构秘钥表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ORGANIZATION_ROUTE")
public class OrganizationRoute extends Model<OrganizationRoute> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ROUTE_ID")
    private Long routeId;
    /**
     * 平安省份编码
     */
    @TableField("MERCHANT_PROVINCE_ID")
    private String merchantProvinceId;
    /**
     * 平安省份名
     */
    @TableField("MERCHANT_PROVINCE_NAME")
    private String merchantProvinceName;
    /**
     * 平安城市编码
     */
    @TableField("MERCHANT_CITY_ID")
    private String merchantCityId;
    /**
     * 平安城市名
     */
    @TableField("MERCHANT_CITY_NAME")
    private String merchantCityName;
    /**
     * 机构编号
     */
    @TableField("ORGANIZATON_ID")
    private String organizatonId;
    /**
     * 密钥
     */
    @TableField("SECRETKEY")
    private String secretkey;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.routeId;
    }

}
