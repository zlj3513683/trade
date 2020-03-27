package com.posppay.newpay.modules.xposp.router.channel.starpos.model;


/**
 * 功能： 路由枚举
 *
 * @author zengjw
 */

public enum PayResult {

    /**
     * 路由枚举
     */
    SUCCESS("S", "交易成功"),
    FAIL("F", "交易失败"),
    WAITAUTH("A", "等待授权"),
    UNKNOW("Z", "交易未知");


    private String code;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     *
     * @param code
     * @return
     */
    public static PayResult fromCode(String code) {
        for (PayResult payChannelType : PayResult.values()) {
            if (payChannelType.getCode().equals(code)) {
                return payChannelType;
            }
        }
        return null;
    }

    PayResult(String code, String chnName) {
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
