package com.posppay.newpay.modules.xposp.entity;

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
 * 门店信息表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_STOE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmStoe extends Model<UrmStoe> {

    private static final long serialVersionUID = 1L;

    /**
     * 门店号
     */
    @TableId("STOE_ID")
    private String stoeId;
    /**
     * 商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 内部用户号
     */
    @TableField("USR_NO")
    private String usrNo;
    /**
     * 门店名称
     */
    @TableField("STOE_NM")
    private String stoeNm;
    /**
     * 省份编码
     */
    @TableField("STOE_PROV")
    private String stoeProv;
    /**
     * 城市编码
     */
    @TableField("STOE_CITY")
    private String stoeCity;
    /**
     * 门店地址
     */
    @TableField("STOE_ADDS")
    private String stoeAdds;
    /**
     * 联系人名称
     */
    @TableField("STOE_CNT_NM")
    private String stoeCntNm;
    /**
     * 联系人电话
     */
    @TableField("STOE_CNT_TEL")
    private String stoeCntTel;
    /**
     * mcc码
     */
    @TableField("MCC_CD")
    private String mccCd;
    /**
     * 营业开始时间
     */
    @TableField("BUS_STR_TIME")
    private String busStrTime;
    /**
     * 营业结束时间
     */
    @TableField("BUS_END_TIME")
    private String busEndTime;
    /**
     * 签约机构
     */
    @TableField("SIG_ORG_NO")
    private String sigOrgNo;
    /**
     * 落地机构
     */
    @TableField("OWN_ORG_NO")
    private String ownOrgNo;
    /**
     * 协议签订日期
     */
    @TableField("EFF_DT")
    private String effDt;
    /**
     * 协议到期日期
     */
    @TableField("EXP_DT")
    private String expDt;
    /**
     * 销售人员名称
     */
    @TableField("CUS_MGR_NM")
    private String cusMgrNm;
    /**
     * 邮政编码
     */
    @TableField("MAIL_ZIP")
    private String mailZip;
    /**
     * 优惠类型
     */
    @TableField("BENF_TYP")
    private String benfTyp;
    /**
     * 门头照
     */
    @TableField("DOOR_IMG")
    private String doorImg;
    /**
     * 店内场景照
     */
    @TableField("FOY_IMG")
    private String foyImg;
    /**
     * 收银台照
     */
    @TableField("CHOC_IMG")
    private String chocImg;
    /**
     * 电子账户属性，默认公用商户电子账户
     */
    @TableField("AC_FLG")
    private String acFlg;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 代理商编号
     */
    @TableField("AGT_MERC_ID")
    private String agtMercId;
    /**
     * 店内场景照2
     */
    @TableField("FOY_IMG2")
    private String foyImg2;
    /**
     * mcc大类
     */
    @TableField("SUP_MCC_CD")
    private String supMccCd;
    /**
     * mcc小类
     */
    @TableField("MCC_TYP")
    private String mccTyp;
    /**
     * 区域码
     */
    @TableField("STOE_AREA_COD")
    private String stoeAreaCod;
    @TableField("SYN_STS")
    private String synSts;
    @TableField("SYN_BATCH")
    private String synBatch;
    @TableField("SITE_REG_COD")
    private String siteRegCod;
    /**
     * 小票商户名
     */
    @TableField("STOE_CNM")
    private String stoeCnm;
    /**
     * 是否支持双免 0:不支持 1:支持
     */
    @TableField("SUPT_DBFREE_FLG")
    private String suptDbfreeFlg;
    /**
     * 免密金额
     */
    @TableField("NO_PSWD_LIMIT")
    private String noPswdLimit;
    /**
     * 免签金额
     */
    @TableField("NO_SIGN_LIMIT")
    private String noSignLimit;
    /**
     * 手续费
     */
    @TableField("SER_FEE_TYP")
    private String serFeeTyp;
    @TableField("SYN_UPD_STS")
    private String synUpdSts;
    /**
     * 终端限制数量
     */
    @TableField("TRM_LIMIT_NUM")
    private String trmLimitNum;
    /**
     * 商户退款标志
     */
    @TableField("RETGOOD_STS")
    private String retgoodSts;
    @TableField("MERC498_FAIL_REASON")
    private String merc498FailReason;
    @TableField("MERC498_STATS")
    private String merc498Stats;
    @TableField("DX_CHANNEL")
    private String dxChannel;
    @TableField("SHJR_CHANNEL")
    private String shjrChannel;
    @TableField("WZH_CHANNEL")
    private String wzhChannel;
    @TableField("WZH_WXCHANNEL_ID")
    private String wzhWxchannelId;
    @TableField("WZH_PUBNUM_STATS")
    private String wzhPubnumStats;
    @TableField("WZH_RECOM_STATS")
    private String wzhRecomStats;
    /**
     * 扫码小票商户号
     */
    @TableField("SCAN_STOE_CNM")
    private String scanStoeCnm;
    @TableField("WX_APPID")
    private String wxAppid;
    @TableField("WX_APPSECRECT")
    private String wxAppsecrect;
    @TableField("WX_PAY_CATALOG")
    private String wxPayCatalog;
    @TableField("RECOM_WX_APPID")
    private String recomWxAppid;
    @TableField("WZH_RECOM_STATS2")
    private String wzhRecomStats2;
    @TableField("WZH_CHANNEL_ALI")
    private String wzhChannelAli;
    /**
     * 门店状态
     */
    @TableField("STOE_STS")
    private String stoeSts;
    @TableField("STOE_STS_TMP")
    private String stoeStsTmp;
    @TableField("MERC_ADDSTATS")
    private String mercAddstats;
    @TableField("PBANK_MERCID")
    private String pbankMercid;
    @TableField("WX_ADDSTSTS")
    private String wxAddststs;
    @TableField("APLI_ADDSTSTS")
    private String apliAddststs;
    @TableField("PBANK_STOEID")
    private String pbankStoeid;
    @TableField("OPENID_ADDSTSTS")
    private String openidAddststs;
    @TableField("PBANK_ERRORMESS")
    private String pbankErrormess;
    @TableField("WZH_AGENT_NO")
    private String wzhAgentNo;
    @TableField("WZH_CHANNEL_ERROR_DES")
    private String wzhChannelErrorDes;
    @TableField("PANK_APLI_ID")
    private String pankApliId;
    @TableField("PANK_WX_ID")
    private String pankWxId;
    @TableField("PANK_APPLYID_RELASTATS")
    private String pankApplyidRelastats;
    @TableField("PANK_APPLYID_RECOMSTATS")
    private String pankApplyidRecomstats;
    @TableField("PANK_APPLYID_RELA")
    private String pankApplyidRela;
    @TableField("PANK_APPLYID_RECOM")
    private String pankApplyidRecom;
    @TableField("PANK_PAY_CATALOG")
    private String pankPayCatalog;
    @TableField("PANK_PAY_CATALOGSTATS")
    private String pankPayCatalogstats;
    @TableField("OPEN_ACCOUNT_STATS")
    private String openAccountStats;
    @TableField("PANK_APPLYSECRECT_REAL")
    private String pankApplysecrectReal;
    @TableField("STOE_CDT")
    private String stoeCdt;
    @TableField("CUSTOMER_CALL")
    private String customerCall;
    /**
     * 门店英文名
     */
    @TableField("ENGLISH_NM")
    private String englishNm;
    @TableField("WX_INF")
    private String wxInf;
    @TableField("CHANNEL_NO")
    private String channelNo;
    @TableField("RECOM_POS_APPID")
    private String recomPosAppid;
    @TableField("RECOM_POS_APPSECRECT")
    private String recomPosAppsecrect;
    @TableField("SMPRO_APPID")
    private String smproAppid;
    @TableField("SMPRO_APPSECRECT")
    private String smproAppsecrect;
    @TableField("SMPRO_STATS")
    private String smproStats;
    @TableField("RECOM_APPID")
    private String recomAppid;
    @TableField("RECOM_APPSECRECT")
    private String recomAppsecrect;
    @TableField("RECOM_PUB_STATS")
    private String recomPubStats;
    @TableField("RECOM_POS_STATS")
    private String recomPosStats;
    @TableField("WX_LZH_INF")
    private String wxLzhInf;
    @TableField("WX_LZH_STS")
    private String wxLzhSts;
    @TableField("CHANNEL_NO_LZH")
    private String channelNoLzh;
    /**
     * 会员开通状态
     */
    @TableField("MB_OPEN_STS")
    private String mbOpenSts;
    /**
     * 结算模式
     */
    @TableField("MB_STL_MOD")
    private String mbStlMod;
    /**
     * 操作标志
     */
    @TableField("DAT_TYP")
    private String datTyp;
    /**
     * 行业类别
     */
    @TableField("INDUSTRY_CODE")
    private String industryCode;
    /**
     * 支付宝pid
     */
    @TableField("ALI_PID")
    private String aliPid;
    /**
     * 微信第三方商户号
     */
    @TableField("WX_THIRD_MERC_NO")
    private String wxThirdMercNo;
    /**
     * 联系人身份证号
     */
    @TableField("STOE_CNT_ID_NO")
    private String stoeCntIdNo;


    @Override
    protected Serializable pkVal() {
        return this.stoeId;
    }

}
