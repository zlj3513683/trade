package com.posppay.newpay.modules.model;


/**
 * 功能：交易类型枚举
 *
 * @author zengjw
 */

public enum ResultStatus {


    SUCCESS("S", "成功"),
    REFUND("C", "被冲正"),
    NOTPAY("U", "预记状态"),
    CLOSED("X", "发送失败"),
    CANCEL("T", "发送超时"),
    USEPAY("E", "其他错误"),
    FAIL("F", "支付失败"),
    PROCESS("P", "处理中");
    private String code;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     *
     * @param code
     * @return
     */
    public static ResultStatus fromCode(String code) {
        for (ResultStatus payType : ResultStatus.values()) {
            if (payType.getCode().equals(code)) {
                return payType;
            }
        }
        return null;
    }

    ResultStatus(String code, String chnName) {
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
