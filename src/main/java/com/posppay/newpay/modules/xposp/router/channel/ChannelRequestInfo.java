package com.posppay.newpay.modules.xposp.router.channel;

import com.posppay.newpay.modules.sdk.pingan.model.ErrCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zengjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelRequestInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易凭证号
     */
    private String payNo;


    /**
     * 原始交易流水号
     */
    private String originalPayNo;
    private String cardNo;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 交易所属订单号
     */
    private String orderNo;
    private String track2data;
    private String track3data;
    /**
     * pin
     */
    private String pinblock;
    /**
     * IC卡交易数据
     */
    private String emvTransInfo;
    /**
     * 卡片序列号
     */
    private String cardSeqNum;
    /**
     * 卡片输入模式
     */
    private String entryMode;
    /**
     * 币种
     */
    private String currency;
    /**
     * 打印字段
     */
    private String printData;
    /**
     * 卡有效期
     */
    private String cardExpiryDay;
    /**
     * 设备主键
     */
    private String devId;
    /**
     * 设备信息
     */
    private String deviceInfo;
    /**
     * 付款码
     */
    private String authCode;
    /**
     * 商户名称
     */
    private String mrchName;
    /**
     * 原始渠道流水号
     */
    private String orginChnFlowId;
    private String trmnlReferNo;
    private String trmnlBatchNo;
    /**
     * 电子签名数据
     */
    private String elecsignData;
    /**
     * 国通渠道时该字段可存放logNo,chnOrdNo,tradeNo
     */
    private String chnOrderNo;
    /**
     * 渠道号
     */
    private String chnId;
    /**
     * 服务点条件码
     */
    private String serviceCode;
    /**
     * 终端批次号
     */
    private String batchNo;
    /**
     * 设备批次号
     */
    private String devBatchNo;
    /**
     * 7域交易时间
     */
    private String transTime;
    /**
     * 12域交易时间
     */
    private String transHour;
    /**
     * 13域交易日期
     */
    private String transDate;
    /**
     * 清算时间
     */
    private String settlementDate;
    /**
     * 受卡方所在地址hex类型
     */
    private String cardholderAddress;
    /**
     * 原始交易信息
     */
    private String oriConsumeInfo;
    /**
     * 原始参考号
     */
    private String oriTrmnlReferno;
    /**
     * 48域
     * 秘钥重置时加密机生成的秘钥 zmk加密
     */
    private String selfDefined048;
    /**
     * mak lmk加密
     */
    private String makEncrpyByLmk;

    private String storeNo;
    private String storeName;
    /**
     * 秘钥类型
     * pin/mac
     */
    private int keyType;
    /**
     * 卡机构标识
     */
    private String cardOrgNo;
    /**
     * 渠道保底费率
     */
    private BigDecimal channelRates;
    /**
     * 费率百分比
     */
    private BigDecimal splitRates;
    /**
     * 商户费率
     */
    private BigDecimal merchantRate;
    /**
     * pos流水号
     */
    private String posFlowNo;

    /**
     * 机构主密钥
     */
    private String OrgMkey;
    /**
     * 57域
     */
    private String selfDefined057;
    /**
     * 国密59域
     */
    private String selfDefined059;
    /**
     * 银联订单号
     */
    private String transactionId;
    /**
     * 退款金额
     */
    private BigDecimal refundAmt;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 机构秘钥
     */
    private String screkey;
    /**
     * 机构号
     */
    private String signInstNo;
    /**
     * 设备ip
     */
    private String deviceIp;
    /**
     * 操作员号
     */
    private String operator;
    /**
     * 设备交易时间
     */
    private String deviceTime;
    /**
     * 商品标记
     */
    private String goodsTag;
    /**
     * 商品描述
     */
    private String goodsDetails;
    /**
     * 单品信息
     */
    private String goodsDetail;
    /**
     * 附加信息
     */
    private String attach;
    /**
     * 公众账号ID 【微信】
     */
    private String subppid;
    /**
     * 电子发票【微信】
     */
    private String needReceipt;
    private ErrCodeType errCodeType;
    private String status;
    private String body;
    private String subTransType;
    private String endTime;

    private BigDecimal cupFeeBank;
    private BigDecimal cupFeeCredict;
    private String bankType;
    private String notifyUrl;
    private String openid;
    private String subAppid;
    /**
     * 银联订单号
     */
    private String outTransactionId;
    /**
     * 外部订单号
     */
    private String servOrderNo;
    /**
     * 星pos支付结果
     */
    private String result;
}
