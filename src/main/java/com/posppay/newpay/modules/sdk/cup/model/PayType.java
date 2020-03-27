package com.posppay.newpay.modules.sdk.cup.model;


/**
 * 功能：交易类型枚举
 * @author zengjw
 */

public enum PayType {


    WX_CARD_PAY("pay.weixin.micropay","微信刷卡支付","0001"),
    ALI_CARD_PAY("pay.alipay.micropay","支付宝刷卡支付","0002"),
    JD_CARD_PAY("pay.jdpay.micropay","京东刷卡支付","0003"),
    SHIMING_CARD_PAY("pay.shiming.micropay","会员卡支付","0004"),
    UNION_PAY("pay.unionpay.micropay","银联支付","0005"),
    BEST_PAY("pay.bestpay.micropay","翼支付","0006"),
    ALI_GZH_PAY("pay.alipay.jspay","支付宝公众号支付","0007"),
    WX_GZH_PAY("pay.weixin.jspay","微信公众号支付","0008"),
    YL_GZH_PAY("pay.unionpay.jspay","银联公众号支付","0009"),
    ALI_SCAN_PAY("pay.alipay.native","支付宝扫码支付","0010"),
    WX_SCAN_PAY("pay.weixin.native","微信扫码支付","0011"),
    YL_SCAN_PAY("pay.unionpay.native","银联扫码支付","0012");



    private String engName;
    private String chnName;
    private String code;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param code
     * @return
     */
    public static PayType fromCode(String code){
        for (PayType payType : PayType.values()) {
            if (payType.getCode().equals(code)) {
                return payType;
            }
        }
        return null;
    }
    /**
     * 功能： 根据engName来获取具体的枚举
     * @param engName
     * @return
     */
    public static PayType fromEngName(String engName){
        for (PayType payType : PayType.values()) {
            if (payType.getEngName().equals(engName)) {
                return payType;
            }
        }
        return null;
    }
    PayType(String engName,String chnName,String code) {
        this.engName = engName;

        this.chnName=chnName;
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }
}
