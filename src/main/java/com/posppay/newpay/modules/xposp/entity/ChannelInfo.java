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

/**
 * <p>
 * 渠道信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CHANNEL_INFO")
public class ChannelInfo extends Model<ChannelInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id号
     */
    @TableId("CHN_ID")
    private String chnId;
    /**
     * 渠道名称
     */
    @TableField("CHN_NAME")
    private String chnName;
    /**
     * 渠道码
     */
    @TableField("CHN_CODE")
    private String chnCode;
    /**
     * 是否支持银行卡交易
     */
    @TableField("POS_PAY")
    private String posPay;
    /**
     * 是否支持扫码交易
     */
    @TableField("SCAN_PAY")
    private String scanPay;
    @TableField("TM_SMP")
    private String tmSmp;
    @TableField("UPD_TM")
    private String updTm;
    /**
     * 支付宝通道费率
     */
    @TableField("ALI_FEE")
    private BigDecimal aliFee;
    /**
     * 微信通道费率
     */
    @TableField("WX_FEE")
    private BigDecimal wxFee;
    /**
     * 银联二维码大于1000费率
     */
    @TableField("YL_COMMON_CREDICT_FEE")
    private BigDecimal ylCommonCredictFee;
    /**
     * 银联二维码小额贷记卡费率
     */
    @TableField("YL_MICRO_CREDICT_FEE")
    private BigDecimal ylMicroCredictFee;
    /**
     * 银联二维码小额借记卡费率
     */
    @TableField("YL_MICRO_BANK_FEE")
    private BigDecimal ylMicroBankFee;
    /**
     * 银联二维码大于1000借记卡费率
     */
    @TableField("YL_COMMON_BANK_FEE")
    private BigDecimal ylCommonBankFee;
    /**
     * 支付宝JS费率
     */
    @TableField("ALI_JS_FEE")
    private BigDecimal aliJsFee;
    /**
     * 微信台牌费率
     */
    @TableField("WX_TP_FEE")
    private BigDecimal wxTpFee;
    /**
     * 银联封顶金额
     */
    @TableField("YL_QR_COMMON_BANK_FEE_MAX")
    private BigDecimal ylQrCommonBankFeeMax;


    @Override
    protected Serializable pkVal() {
        return this.chnId;
    }

}
