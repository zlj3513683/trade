package com.posppay.newpay.modules.model;


/**
 * 功能： 权限枚举
 *
 * @author zengjw
 */

public enum PayChannelType {

    /**
     * 权限枚举
     */
   ALI_CHANNEL("ALIPAY","支付宝被扫"),
   WX_CHANNEL("WXPAY","微信被扫"),
   CUP_CHANNEL("YLPAY","银联被扫"),
   ALI_ZS("ALISCAN","支付宝主扫"),
   WX_ZS("WXSCAN","微信主扫"),
   CUP_ZS("YLSCAN","银联主扫"),
   ALI_GZH("ALIGZH","支付宝公众号"),
   WX_GZH("WXGZH","微信公众号"),
   CUP_GZH("YLGZH","银联公众号");



    private String code;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param code
     * @return
     */
    public static PayChannelType fromCode(String code){
        for (PayChannelType payChannelType : PayChannelType.values()) {
            if (payChannelType.getCode().equals(code)) {
                return payChannelType;
            }
        }
        return null;
    }
    PayChannelType(String code, String chnName) {
      this.code=code;
      this.chnName=chnName;
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
}
