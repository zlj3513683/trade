package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 系统订单表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ORDER")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单表主键
     */
    @TableId("ORD_ID")
    private String ordId;
    /**
     * 系统订单号
     */
    @TableField("ORD_NO")
    private String ordNo;
    /**
     * 订单描述
     */
    @TableField("ORD_DESC")
    private String ordDesc;
    /**
     * 订单金额
     */
    @TableField("ORD_AMT")
    private BigDecimal ordAmt;
    /**
     * 商户号
     */
    @TableField("MRCH_NO")
    private String mrchNo;
    /**
     * 商户名称
     */
    @TableField("MRCH_NAME")
    private String mrchName;


    /**
     * 订单状态
     */
    @TableField("ORD_STATUS")
    private String ordStatus;
    /**
     * 货币代码
     */
    @TableField("CURRENCY")
    private String currency;
    /**
     * 渠道标识
     */
    @TableField("CHN_CODE")
    private String chnCode;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("CRE_TIME")
    private Date creTime;
    /**
     * 更新时间
     */
    @TableField("UPD_TIME")
    private Date updTime;
    /**
     * 系统门店号
     */
    @TableField("STORE_NO")
    private String storeNo;
    /**
     * 门店名
     */
    @TableField("STORE_NAME")
    private String storeName;
    /**
     * 剩余可退金额
     */
    @TableField("REFUND_BALANCE")
    private BigDecimal refundBalance;
    /**
     * 设备sn
     */
    @TableField("DEV_SN")
    private String devSn;
    /**
     *  下游机构号
     */
    @TableField("ORG_NO")
    private String orgNo;


    @Override
    protected Serializable pkVal() {
        return this.ordId;
    }

}
