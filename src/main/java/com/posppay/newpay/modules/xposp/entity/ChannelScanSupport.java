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
 * 扫码渠道支持表
 * </p>
 *
 * @author zengjw
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CHANNEL_SCAN_SUPPORT")
public class ChannelScanSupport extends Model<ChannelScanSupport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("CHANNEL_SCAN_SUPPORT_ID")
    private String channelScanSupportId;
    /**
     * 渠道号
     */
    @TableField("CHN_CODE")
    private String chnCode;
    /**
     * 微信扫码交易是否支持
1代表支持
0代表不支持
     */
    @TableField("WX_SCAN_PAY_SUPPORT")
    private String wxScanPaySupport;
    /**
     * 支付宝扫码交易是否支持
1代表支持
0代表不支持
     */
    @TableField("ALI_SCAN_PAY_SUPPORT")
    private String aliScanPaySupport;
    /**
     * 银联扫码交易是否支持
1代表支持
0代表不支持
     */
    @TableField("CUP_SCAN_PAY_SUPPORT")
    private String cupScanPaySupport;
    /**
     * 微信进件是否支持
1代表支持
0代表不支持
     */
    @TableField("WX_IN_COME_SUPPORT")
    private String wxInComeSupport;
    /**
     * 支付宝进件是否支持
1代表支持
0代表不支持
     */
    @TableField("ALI_IN_COME_SUPPORT")
    private String aliInComeSupport;
    /**
     * 云闪付进件是否支持
1代表支持
0代表不支持
     */
    @TableField("CUP_IN_COME_SUPPORT")
    private String cupInComeSupport;
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
     * 商户类型
     * 1.小微
     * 2.普通
     */
    @TableField("MERC_TYPE")
    private String mercType;
    /**
     * 公众号支付是否支持 1代表支持 0代表不支持
     */
    @TableField("SUB_SCRIBE_PAY")
    private String subScribePay;
    /**
     * C扫B支付宝动态码支付是否支持1代表支持0 代表不支持
     */
    @TableField("QR_CODE_ALI_PAY")
    private String qrCodeAliPay;
    /**
     * C扫B微信动态码支付是否支持1代表支持0 代表不支持
     */
    @TableField("QR_CODE_WX_PAY")
    private String qrCodeWxPay;
    /**
     * C扫B云闪付动态码支付是否支持1代表支持0 代表不支持
     */
    @TableField("QR_CODE_CUP_PAY")
    private String qrCodeCupPay;



    @Override
    protected Serializable pkVal() {
        return this.channelScanSupportId;
    }

}
