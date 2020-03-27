package com.posppay.newpay.modules.xposp.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 交易流水表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_HPS_JNL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HpsJnl extends Model<HpsJnl> {

    private static final long serialVersionUID = 1L;

    /**
     * 跟踪号
     */
    @TableId("LOG_NO")
    private String logNo;
    /**
     * 交易日期
     */
    @TableField("AC_DT")
    private String acDt;
    /**
     * 交易类型 N:正常交易 C:被冲正交易 D:被撤销交易
     */
    @TableField("TXN_TYP")
    private String txnTyp;
    /**
     * 交易码
     */
    @TableField("TXN_CD")
    private String txnCd;
    /**
     * 渠道号
     */
    @TableField("CORG_NO")
    private String corgNo;
    /**
     * 外发服务器
     */
    @TableField("RTR_SVR")
    private String rtrSvr;
    /**
     * 外发交易码
     */
    @TableField("RTR_COD")
    private String rtrCod;
    /**
     * 前置交易时间
     */
    @TableField("TXN_TM")
    private String txnTm;
    /**
     * 组件返回码
     */
    @TableField("MSG_CD")
    private String msgCd;
    /**
     * 增值服务平台编码
     */
    @TableField("BSVR_CD")
    private String bsvrCd;
    /**
     * 增值服务平台交易日期
     */
    @TableField("BTX_DT")
    private String btxDt;
    /**
     * 增值服务平台流水号
     */
    @TableField("BLOG_NO")
    private String blogNo;
    /**
     * 增值服务平台响应码
     */
    @TableField("BRSP_CD")
    private String brspCd;
    /**
     * 增值服务平台交易状态S:成功
     F:失败
     C:被冲正
     U:预记状态
     X:发送失败
     T: 发送超时
     E: 其他错误
     */
    @TableField("BTX_ST")
    private String btxSt;
    /**
     * 第三方帐务日期
     */
    @TableField("TACC_DT")
    private String taccDt;
    /**
     * 第三方流水号
     */
    @TableField("TLOG_NO")
    private String tlogNo;
    /**
     * 第三方响应码
     */
    @TableField("TRSP_CD")
    private String trspCd;
    /**
     * 第三方交易状态S:成功
     F:失败
     C:被冲正
     U:预记状态
     X:发送失败
     T: 发送超时
     E: 其他错误
     */
    @TableField("TTXN_STS")
    private String ttxnSts;
    /**
     * 与第三方对帐标志0：未对账
     1：对账成功
     2：我方有对方无
     3：对方有我方无
     4：关键信息不符
     */
    @TableField("THD_CHK_FLG")
    private String thdChkFlg;
    /**
     * 与第三方对账日期
     */
    @TableField("CHK_FIL_DT")
    private String chkFilDt;
    /**
     * 对账批次
     */
    @TableField("CHK_BAT_NO")
    private String chkBatNo;
    /**
     * 交易状态S:成功
     F:失败
     C:被冲正
     U:预记状态
     X:发送失败
     T: 发送超时
     E: 其他错误
     */
    @TableField("TXN_STS")
    private String txnSts;
    /**
     * 卡号
     */
    @TableField("CRD_NO")
    private String crdNo;
    /**
     * 卡序列号
     */
    @TableField("CRD_SQN")
    private String crdSqn;
    /**
     * 卡类型00:借记卡 01：贷记卡 02：准贷记卡
     */
    @TableField("CRD_FLG")
    private String crdFlg;
    /**
     * 银行类型
     */
    @TableField("BNK_TYP")
    private String bnkTyp;
    /**
     * 卡有效期
     */
    @TableField("CRD_EXP_DT")
    private String crdExpDt;
    /**
     * 发卡机构号
     */
    @TableField("ISS_ORG_NO")
    private String issOrgNo;
    /**
     * 金额
     */
    @TableField("TXN_AMT")
    private Double txnAmt;
    /**
     * 已退货金额
     */
    @TableField("REF_AMT")
    private Double refAmt;
    /**
     * 合作机构费率类型
     */
    @TableField("CORG_FEE_TYP")
    private String corgFeeTyp;
    /**
     * 合作机构返点率
     */
    @TableField("CORG_REB_RAT")
    private Double corgRebRat;
    /**
     * 合作机构手续费率
     */
    @TableField("CORG_RAT")
    private Double corgRat;
    /**
     * 合作机构手续费
     */
    @TableField("CORG_FEE_AMT")
    private Double corgFeeAmt;
    /**
     * 商户手续费
     */
    @TableField("MERC_FEE_AMT")
    private Double mercFeeAmt;
    /**
     * 返点金额
     */
    @TableField("REB_AMT")
    private Double rebAmt;
    /**
     * 归属机构
     */
    @TableField("ORG_NO")
    private String orgNo;
    /**
     * 批次号
     */
    @TableField("BAT_NO")
    private String batNo;
    /**
     * 外部批次号
     */
    @TableField("CORG_BAT_NO")
    private String corgBatNo;
    /**
     * 币种
     */
    @TableField("CCY")
    private String ccy;
    /**
     * 商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 冒名商户编号
     */
    @TableField("FM_MERC_ID")
    private String fmMercId;
    /**
     * 本贷他标志
     */
    @TableField("BD_FLG")
    private String bdFlg;
    /**
     * 外部商户号
     */
    @TableField("CORG_MERC_ID")
    private String corgMercId;
    /**
     * 终端号
     */
    @TableField("TRM_NO")
    private String trmNo;
    /**
     * 外部终端号
     */
    @TableField("CORG_TRM_NO")
    private String corgTrmNo;
    /**
     * POS操作员编号
     */
    @TableField("POS_OPR_ID")
    private String posOprId;
    /**
     * C端交易日期
     */
    @TableField("CTXN_DT")
    private String ctxnDt;
    /**
     * C端交易时间
     */
    @TableField("CTXN_TM")
    private String ctxnTm;
    /**
     * C端流水号
     */
    @TableField("CSEQ_NO")
    private String cseqNo;
    /**
     * 外部流水号
     */
    @TableField("CORG_CSEQ_NO")
    private String corgCseqNo;
    /**
     * 输入方式
     */
    @TableField("IN_MOD")
    private String inMod;
    /**
     * 系统参考号
     */
    @TableField("SREF_NO")
    private String srefNo;
    /**
     * 授权码
     */
    @TableField("AUT_CD")
    private String autCd;
    /**
     * 返回码
     */
    @TableField("CPS_CD")
    private String cpsCd;
    /**
     * 代理机构标识码
     */
    @TableField("ACQ_NO")
    private String acqNo;
    /**
     * 发送机构标识码
     */
    @TableField("FOR_COD")
    private String forCod;
    /**
     * 是否支持外卡
     */
    @TableField("EMV_FLG")
    private String emvFlg;
    /**
     * EMV信息域
     */
    @TableField("EMV_DAT")
    private String emvDat;
    /**
     * EMV信息长度
     */
    @TableField("EMV_DAT_LEN")
    private String emvDatLen;
    /**
     * 原交易流水号
     */
    @TableField("OLOG_NO")
    private String ologNo;
    /**
     * 消息处理码
     */
    @TableField("PRO_COD")
    private String proCod;
    /**
     * 码
     */
    @TableField("T_PRO_COD")
    private String tProCod;
    /**
     * 商户类别
     */
    @TableField("MCC_CD")
    private String mccCd;
    /**
     * 商户名称
     */
    @TableField("MERC_NM")
    private String mercNm;
    /**
     * 服务点条件码
     */
    @TableField("POS_CND")
    private String posCnd;
    /**
     * 保留域
     */
    @TableField("RSV_DAT")
    private String rsvDat;
    /**
     * 原始数据域
     */
    @TableField("ORAN_DAT")
    private String oranDat;
    /**
     * 转接方保留域
     */
    @TableField("CPS_RSV")
    private String cpsRsv;
    /**
     * 受理方保留域
     */
    @TableField("ACQ_RSV")
    private String acqRsv;
    /**
     * 外卡批结标志
     */
    @TableField("END_FLG")
    private String endFlg;
    /**
     * 8583第7域
     */
    @TableField("TMS_TM")
    private String tmsTm;
    /**
     * 用户ID
     */
    @TableField("USR_ID")
    private Double usrId;
    /**
     * 内部用户号
     */
    @TableField("USR_NO")
    private String usrNo;
    /**
     * 清分标志
     */
    @TableField("CLR_FLG")
    private String clrFlg;
    /**
     * 清分日期
     */
    @TableField("CLR_DT")
    private String clrDt;
    /**
     * 会员编号
     */
    @TableField("MEM_NO")
    private String memNo;
    /**
     * 是否使用优惠券
     */
    @TableField("COUP_FLG")
    private String coupFlg;
    /**
     * 优惠券信息
     */
    @TableField("MEMBER_COU")
    private String memberCou;
    /**
     * 优惠券号
     */
    @TableField("COU_ID")
    private String couId;
    /**
     * 清算标志
     */
    @TableField("STL_STS")
    private String stlSts;
    /**
     * 清算日期
     */
    @TableField("STL_DT")
    private String stlDt;
    /**
     * 保留域1（境外卡交易时返回卡等级 1-普卡 3-金卡4-白金卡 5-钻石卡）
     */
    @TableField("TXN_RSV1")
    private String txnRsv1;
    /**
     * 保留域2
     */
    @TableField("TXN_RSV2")
    private String txnRsv2;
    /**
     * 保留域3
     */
    @TableField("TXN_RSV3")
    private String txnRsv3;
    /**
     * 接入机构编号
     */
    @TableField("SYS_ID")
    private String sysId;
    /**
     * 接入机构支付流水号
     */
    @TableField("PAY_JNL")
    private String payJnl;
    /**
     * 是否押金保证金交易
     */
    @TableField("IS_SPE")
    private String isSpe;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 上级商户编号
     */
    @TableField("UP_MERC_ID")
    private String upMercId;
    /**
     * 上级商户名称
     */
    @TableField("UP_MERC_NM")
    private String upMercNm;
    /**
     * 银行预收手续费
     */
    @TableField("CORG_PER_FEE")
    private Double corgPerFee;
    /**
     * 代理商分润金额
     */
    @TableField("AGT_FEE_AMT")
    private Double agtFeeAmt;
    @TableField("TXN_RSV4")
    private Double txnRsv4;
    /**
     * 合作商户号
     */
    @TableField("CORGR_MERC_ID")
    private String corgrMercId;
    /**
     * 合作商终端号
     */
    @TableField("CORGR_TRM_NO")
    private String corgrTrmNo;
    /**
     * 超级码费率标志
     */
    @TableField("F46")
    private String f46;
    /**
     * 微信appid
     */
    @TableField("F47")
    private String f47;
    /**
     * 合作机构参考号
     */
    @TableField("CORG_SREF_NO")
    private String corgSrefNo;
    /**
     * 商户手续费归属机构
     */
    @TableField("FEE_ORG_NO")
    private String feeOrgNo;
    /**
     * 卡片序列号
     */
    @TableField("SEP_NUM")
    private String sepNum;
    @TableField("JRE_NO")
    private String jreNo;
    /**
     * 电子签名文件路径
     */
    @TableField("TXN_SIG_IMG")
    private String txnSigImg;
    @TableField("WD_STS")
    private String wdSts;
    @TableField("STL_MOD")
    private String stlMod;
    @TableField("EP_TYP")
    private String epTyp;
    @TableField("OFF_LIN_FLG")
    private String offLinFlg;
    @TableField("RCP_ORD_ID")
    private String rcpOrdId;
    @TableField("RCP_AUT_CD")
    private String rcpAutCd;
    @TableField("TXN_CNL")
    private String txnCnl;
    /**
     * 支付渠道
     */
    @TableField("PAYCHANNEL")
    private String paychannel;
    /**
     * 门店号
     */
    @TableField("STOE_ID")
    private String stoeId;
    /**
     * 订单号
     */
    @TableField("ORDERNO")
    private String orderno;
    /**
     * 用户号
     */
    @TableField("USERID")
    private String userid;
    /**
     * 支付时间
     */
    @TableField("PAYTIME")
    private String paytime;
    /**
     * 支付方式
     */
    @TableField("PAYTYPE")
    private String paytype;
    @TableField("AGT_MERC_ID")
    private String agtMercId;
    /**
     * 渠道订单号
     */
    @TableField("CHANNELID")
    private String channelid;
    @TableField("OFFICEID")
    private String officeid;
    @TableField("BANKTYPE")
    private String banktype;
    @TableField("SERV_ORDER_NO")
    private String servOrderNo;
    @TableField("TRSPDT")
    private String trspdt;
    @TableField("LATITUDE")
    private String latitude;
    @TableField("LONGITUDE")
    private String longitude;
    @TableField("TOTAL_AMOUNT")
    private Double totalAmount;
    @TableField("SUBJECT")
    private String subject;
    @TableField("GOODS_TAG")
    private String goodsTag;
    @TableField("ATTACH")
    private String attach;
    @TableField("DEVICE_NO")
    private String deviceNo;
    @TableField("OWN_ORG_NO")
    private String ownOrgNo;
    @TableField("CSN_NO")
    private String csnNo;
    /**
     * 平台sn号
     */
    @TableField("SN_NO")
    private String snNo;
    /**
     * 结算周期
     */
    @TableField("STL_TYP")
    private String stlTyp;
    @TableField("MSH_MERC_ID")
    private String mshMercId;
    /**
     * 结算批次号
     */
    @TableField("STL_BATNO")
    private String stlBatno;
    /**
     * 打款批次号
     */
    @TableField("RMT_BATNO")
    private String rmtBatno;
    /**
     * 打款日期
     */
    @TableField("RMT_DT")
    private String rmtDt;
    /**
     * 打款状态
     */
    @TableField("RMT_STS")
    private String rmtSts;
    /**
     * d0服务费
     */
    @TableField("SERVFEED0")
    private Double servfeed0;
    /**
     * t1手续费
     */
    @TableField("BASEFEEDT1")
    private Double basefeedt1;
    @TableField("FLOW_SMMERC")
    private String flowSmmerc;
    @TableField("FLOW_TRM")
    private String flowTrm;
    @TableField("FLOW_BKG")
    private String flowBkg;
    @TableField("ADDFIELD")
    private String addfield;
    /**
     * D1服务费
     */
    @TableField("SERVFEED1")
    private Double servfeed1;
    @TableField("TRAN_TYP")
    private String tranTyp;
    /**
     * 商户类型
     */
    @TableField("MERC_TYPE")
    private String mercType;
    /**
     * 银联费率类型
     */
    @TableField("YL_FEE_TYPE")
    private String ylFeeType;
    /**
     * 交易总金额
     */
    @TableField("TOTAL_AMT")
    private Double totalAmt;
    @TableField("PAID_AMT")
    private Double paidAmt;
    /**
     * 记账状态
     */
    @TableField("ACC_STS")
    private String accSts;
    /**
     * 记账返回码
     */
    @TableField("ACC_RSP_CD")
    private String accRspCd;
    /**
     * 记账返回信息
     */
    @TableField("ACC_RSP_MSG")
    private String accRspMsg;
    /**
     * 支付模式01：银行卡业务 02, 扫码业务
     */
    @TableField("PAY_MOD")
    private String payMod;
    /**
     * 记账日期，交易时国通返回的清算日期
     */
    @TableField("BALDATE")
    private String baldate;
    @TableField("TRANSACTION_MODE")
    private String transactionMode;


    @Override
    protected Serializable pkVal() {
        return this.logNo;
    }

}
