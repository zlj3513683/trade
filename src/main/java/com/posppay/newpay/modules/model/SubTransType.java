package com.posppay.newpay.modules.model;


/**
 * 功能： 权限枚举
 *
 * @author zengjw
 */

public enum SubTransType {

    /**
     * 渠道机构枚举
     */
   wx("CUP","微信刷卡支付","pay.weixin.micropay"),
   STARPOS_CHANNEL("STARPOS","星pos渠道","starPosTransChannel"),
   PINGAN_CHANNEL("PINGAN","平安渠道","pinganTransChannel");



    private String orgNo;
    private String chnName;
    private String engName;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param orgNo
     * @return
     */
    public static SubTransType fromOrgNo(String orgNo){
        for (SubTransType orgChannelType : SubTransType.values()) {
            if (orgChannelType.getOrgNo().equals(orgNo)) {
                return orgChannelType;
            }
        }
        return null;
    }
    SubTransType(String orgNo, String chnName, String engName) {
      this.orgNo=orgNo;
      this.chnName=chnName;
      this.engName=engName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
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
