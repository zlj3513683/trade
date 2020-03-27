package com.posppay.newpay.modules.model;


/**
 * 功能： 权限枚举
 *
 * @author zengjw
 */

public enum OrgChannelType {

    /**
     * 渠道机构枚举
     */
   CUP_CHANNEL("CUP","银联渠道","cupTransferChannel"),
   STARPOS_CHANNEL("STARPOS","星pos渠道","starPosTransChannel"),
   PINGAN_CHANNEL("PA","平安渠道","pinganTransChannel");



    private String orgNo;
    private String chnName;
    private String engName;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param orgNo
     * @return
     */
    public static OrgChannelType fromOrgNo(String orgNo){
        for (OrgChannelType orgChannelType : OrgChannelType.values()) {
            if (orgChannelType.getOrgNo().equals(orgNo)) {
                return orgChannelType;
            }
        }
        return null;
    }
    OrgChannelType(String orgNo, String chnName,String engName) {
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
