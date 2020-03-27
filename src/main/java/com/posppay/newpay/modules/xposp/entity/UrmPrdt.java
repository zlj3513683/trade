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
 * 产品表
 * </p>
 *
 * @author zengjw
 * @since 2019-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_URM_PRDT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrmPrdt extends Model<UrmPrdt> {

    private static final long serialVersionUID = 1L;

    /**
     * 产品编号
     */
    @TableId("PRD_ID")
    private String prdId;
    /**
     * 产品名称
     */
    @TableField("PRD_NM")
    private String prdNm;
    /**
     * 商户号
     */
    @TableField("MERC_ID")
    private String mercId;
    /**
     * 门店号
     */
    @TableField("STOE_ID")
    private String stoeId;
    /**
     * 时间戳
     */
    @TableField("TM_SMP")
    private String tmSmp;
    /**
     * 支持卡种
     */
    @TableField("CARD_TYP")
    private String cardTyp;
    @TableField("SIG_DT")
    private String sigDt;
    /**
     * 服务费
     */
    @TableField("SERVICE_CHARGE")
    private Double serviceCharge;
    /**
     * 免收金额
     */
    @TableField("WAIVE_AMOUNT")
    private Double waiveAmount;
    /**
     * pos存在标志0不存在 1存在
     */
    @TableField("POS_FLG")
    private String posFlg;
    /**
     * 台牌存在标志0不存在 1存在
     */
    @TableField("TP_FLG")
    private String tpFlg;
    @TableField("ACCEPT_FLAG")
    private String acceptFlag;
    @TableField("WX_BANK_ACCEPT_APPID")
    private String wxBankAcceptAppid;
    @TableField("WX_BANK_APPID")
    private String wxBankAppid;
    @TableField("AUTH_PAYDIR")
    private String authPaydir;
    @TableField("WX_CATE_GORYID")
    private String wxCateGoryid;
    @TableField("SERVICE_PHONE")
    private String servicePhone;
    @TableField("WEIXIN_CHANNELID")
    private String weixinChannelid;
    @TableField("PAY_CODE_INFO")
    private String payCodeInfo;
    @TableField("MAILBOX")
    private String mailbox;
    @TableField("BUSINESS_LICENSE_TYPE")
    private String businessLicenseType;
    /**
     * 户名
     */
    @TableField("MSH_STOE_CNT_NM")
    private String mshStoeCntNm;
    /**
     * 联系电话
     */
    @TableField("MSH_STOE_CNT_TEL")
    private String mshStoeCntTel;
    /**
     * 门店地址
     */
    @TableField("MSH_STOE_ADDS")
    private String mshStoeAdds;
    /**
     * 证件号
     */
    @TableField("MSH_STL_CRP_NO")
    private String mshStlCrpNo;
    /**
     * 结算人姓名
     */
    @TableField("MSH_BNK_ACNM")
    private String mshBnkAcnm;
    /**
     * 结算人账号
     */
    @TableField("MSH_STL_OAC")
    private String mshStlOac;
    /**
     * 营业执照号
     */
    @TableField("MSH_BUS_LIC_NO")
    private String mshBusLicNo;
    @TableField("MSH_PROV_COD")
    private String mshProvCod;
    @TableField("MSH_CITY_COD")
    private String mshCityCod;
    @TableField("MSH_AREA_COD")
    private String mshAreaCod;
    @TableField("MSH_ADDRESS_TYPE")
    private String mshAddressType;
    @TableField("MSH_STATE")
    private String mshState;
    @TableField("MSH_CHNL_STATE")
    private String mshChnlState;
    @TableField("CONTACT_TYPE")
    private String contactType;
    @TableField("ALIPAY_LOGONID")
    private String alipayLogonid;
    @TableField("ALIPAY_BANK_ACCEPT_APPID")
    private String alipayBankAcceptAppid;
    @TableField("ALIPAY_CATE_GORYID")
    private String alipayCateGoryid;
    @TableField("WX_APPLYID")
    private String wxApplyid;
    @TableField("ALIPAY_APPLYID")
    private String alipayApplyid;
    @TableField("AC_TIME")
    private String acTime;
    @TableField("WX_MSH_APPLYSTATE")
    private String wxMshApplystate;
    @TableField("ALIPAY_MSH_APPLYSTATE")
    private String alipayMshApplystate;
    @TableField("BANKSPNAME")
    private String bankspname;
    @TableField("ALIASNAME")
    private String aliasname;
    @TableField("CUR_CORG_NO")
    private String curCorgNo;
    @TableField("CHANNEL_DT")
    private String channelDt;
    @TableField("WX_FLG")
    private String wxFlg;
    @TableField("ALI_FLG")
    private String aliFlg;
    @TableField("SDK_FLG")
    private String sdkFlg;
    @TableField("ELP_FLG")
    private String elpFlg;
    @TableField("ME31_FLG")
    private String me31Flg;


    @Override
    protected Serializable pkVal() {
        return this.prdId;
    }

}
