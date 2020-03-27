package com.posppay.newpay.modules.model;


/**
 * 功能：交易类型枚举
 *
 * @author zengjw
 */

public enum ChannelType {


    INTELPOS("I", "智能pos"),
    APPSCAN("A", "app扫码"),
    PCTRMNL("C", "PC收银端"),
    BILLBOARD("T", "台牌");
    private String code;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     *
     * @param code
     * @return
     */
    public static ChannelType fromCode(String code) {
        for (ChannelType payType : ChannelType.values()) {
            if (payType.getCode().equals(code)) {
                return payType;
            }
        }
        return null;
    }

    ChannelType(String code, String chnName) {
        this.code = code;
        this.chnName = chnName;
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
