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
 * 星pos扫码渠道流水表
 * </p>
 *
 * @author zengjw
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_QR_CHN_FLOW_GT")
public class QrChnFlowGt extends Model<QrChnFlowGt> {

    private static final long serialVersionUID = 1L;

    /**
     * 流水主键Id
     */
    @TableId("QR_CHN_FLOW_GT_ID")
    private String qrChnFlowGtId;
    /**
     *  星pos商户号
     */
    @TableField("MERC_ID_GT")
    private String mercIdGt;
    /**
     * 平台商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 平台门店名称
     */
    @TableField("STOE_NAME")
    private String stoeName;
    /**
     * 平台商户名称
     */
    @TableField("MERC_NAME")
    private String mercName;
    /**
     * 星pos终端号
     */
    @TableField("TRMNL_NO_GT")
    private String trmnlNoGt;
    /**
     * 平台终端号
     */
    @TableField("TRMNL_NO")
    private String trmnlNo;
    /**
     * 商户请求号
     */
    @TableField("REQUEST_ID")
    private String requestId;
    /**
     * 接入号码
     */
    @TableField("TEL_NO")
    private String telNo;
    /**
     * 用户ip地址
     */
    @TableField("IP_ADDRESS")
    private String ipAddress;
    /**
     * 编码
     */
    @TableField("CHARACTER_SET")
    private String characterSet;
    /**
     * 平台门店号
     */
    @TableField("STOE_NO")
    private String stoeNo;
    /**
     * 操作员号
     */
    @TableField("OPR_ID")
    private String oprId;
    /**
     * 交易类型(接口名)
     */
    @TableField("SCAN_TYPE")
    private String scanType;
    /**
     * 支付渠道
     */
    @TableField("PAY_CHANNEL")
    private String payChannel;
    /**
     * 版本号
     */
    @TableField("SCAN_VERSION")
    private String scanVersion;
    /**
     * 系统凭证号
     */
    @TableField("ORDER_ID")
    private String orderId;
    /**
     * 外部订单号
     */
    @TableField("SERV_ORDER_NO")
    private String servOrderNo;
    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;
    /**
     * 批次号
     */
    @TableField("BATCH_NO")
    private String batchNo;
    /**
     * 产品类型
     */
    @TableField("TXN_CNL")
    private String txnCnl;
    /**
     * 支付时间
     */
    @TableField("PAY_TIME")
    private Date payTime;
    /**
     * 星pos订单号
     */
    @TableField("GT_ORDER_NO")
    private String gtOrderNo;
    /**
     * 支付类型
1.条码支付,
2.声波支付,
3.二维码支付,
4.线上支付
     */
    @TableField("PAY_TYPE")
    private String payType;
    /**
     * 动态码地址
     */
    @TableField("QR_CODE")
    private String qrCode;
    /**
     * 设备序列号

     */
    @TableField("SN")
    private String sn;
    /**
     * 渠道号
     */
    @TableField("CHN_CODE")
    private String chnCode;
    /**
     * 微信公众账号
     */
    @TableField("APP_ID")
    private String appId;
    /**
     * 微信公众号密钥
     */
    @TableField("APP_KEY")
    private String appKey;
    /**
     * 机构号
     */
    @TableField("ORG_NO")
    private String orgNo;
    /**
     * 授权码
     */
    @TableField("CODE")
    private String code;
    /**
     * 订单标题
     */
    @TableField("SUBJECT")
    private String subject;
    /**
     * 订货订单号
     */
    @TableField("SEL_ORDER_NO")
    private String selOrderNo;
    /**
     * 优惠说明
     */
    @TableField("GOODS_TAG")
    private String goodsTag;
    /**
     * 余额
     */
    @TableField("BALANCE")
    private Double balance;
    /**
     * 附加信息
     */
    @TableField("ATTACH")
    private String attach;
    /**
     * 支付公众号id
     */
    @TableField("TXN_APP_ID")
    private String txnAppId;
    /**
     * 预支付ID
     */
    @TableField("PREPAY_ID")
    private String prepayId;
    /**
     * 支付公众号ID
     */
    @TableField("API_APP_ID")
    private String apiAppId;
    /**
     * 支付时间戳
     */
    @TableField("API_TIMESTAMP")
    private Date apiTimestamp;
    /**
     * 订单详情扩展字符串
     */
    @TableField("API_PACKAGE")
    private String apiPackage;
    /**
     * 流水创建时间
     */
    @TableField("CRE_TIME")
    private Date creTime;
    /**
     * 流水更新时间
     */
    @TableField("UPD_TIME")
    private Date updTime;
    /**
     * 用户唯一标识
     */
    @TableField("OPEN_ID")
    private String openId;
    /**
     * 错误码
     */
    @TableField("RESP_CODE")
    private String respCode;
    /**
     * 错误描述
     */
    @TableField("RESP_MSG")
    private String respMsg;
    /**
     * 交易凭证号
     */
    @TableField("PAY_NO")
    private String payNo;


    @Override
    protected Serializable pkVal() {
        return this.qrChnFlowGtId;
    }

}
