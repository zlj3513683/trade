package com.posppay.newpay.modules.xposp.entity;

import java.util.Date;
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
 * 银联商户表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("T_CUP_MERCHANT")
public class CupMerchant extends Model<CupMerchant> {

    private static final long serialVersionUID = 1L;

    /**
     * id号
     */
    @TableId("CUP_ID")
    private Double cupId;
    /**
     * 商户号
     */
    @TableField("MERCHANT_ID")
    private String merchantId;
    /**
     * 门店号
     */
    @TableField("STOE_ID")
    private String stoeId;
    /**
     * 银联商户id
     */
    @TableField("CUP_MERCHANT_ID")
    private String cupMerchantId;
    /**
     * 审核状态
     */
    @TableField("EXAMINE_STATUS")
    private Integer examineStatus;
    /**
     * 激活状态
     */
    @TableField("ACTIVATE_STATUS")
    private Integer activateStatus;
    /**
     * 审核不通过信息
     */
    @TableField("EXAMINE_REMARK")
    private String examineRemark;
    /**
     * 激活不通过信息
     */
    @TableField("ACTIVATE_REMARK")
    private String activateRemark;
    /**
     * 微信公众支付报备状态
     */
    @TableField("WECHAT_PUBLIC_STATUS")
    private Integer wechatPublicStatus;
    /**
     * 微信付款码报备状态
     */
    @TableField("WECHAT_PAYMENT_CODE_STATUS")
    private Integer wechatPaymentCodeStatus;
    /**
     * 微信扫码支付报备状态
     */
    @TableField("WECHAT_SCAN_CODE_STATUS")
    private Integer wechatScanCodeStatus;
    /**
     * 支付宝服务窗支付报备状态
     */
    @TableField("ALIPAY_SERVICE_WINDOW_STATUS")
    private Integer alipayServiceWindowStatus;
    /**
     * 支付宝付款码支付报备状态
     */
    @TableField("ALIPAY_PAYMENT_CODE_STATUS")
    private Integer alipayPaymentCodeStatus;
    /**
     * 支付宝扫码支付报备状态
     */
    @TableField("ALIPAY_SCAN_CODE_STATUS")
    private Integer alipayScanCodeStatus;
    /**
     * 银联付款码支付报备状态
     */
    @TableField("CUP_PAYMENT_CODE_STATUS")
    private Integer cupPaymentCodeStatus;
    /**
     * 银联扫码支付报备状态
     */
    @TableField("CUP_SCAN_CODE_STATUS")
    private Integer cupScanCodeStatus;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;
    /**
     * 支付秘钥
     */
    @TableField("CUP_PAY_SECRET_KEY")
    private String cupPaySecretKey;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;


    @Override
    protected Serializable pkVal() {
        return this.cupId;
    }

}
