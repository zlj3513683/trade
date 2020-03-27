package com.posppay.newpay.modules.sdk.cup.model;


/**
 * 功能：交易类型枚举
 * @author zengjw
 */

public enum PayStatus {


    SUCCESS("SUCCESS","支付成功"),
    REFUND("REFUND","转入退款"),
    NOTPAY("NOTPAY","未支付"),
    CLOSED("CLOSED","已关闭"),
    CANCEL("REVOKED/REVERSE","已撤销"),
    USEPAY("USERPAYING","用户支付中"),
    FAIL("PAYERROR","支付失败"),
    PROCESSING("PROCESSING","退款处理中"),
    CHANGE("CHANGE","转入代发"),
    REFUNDFAIL("FAIL","退款失败");



    private String code;
    private String chnName;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param code
     * @return
     */
    public static PayStatus fromCode(String code){
        for (PayStatus payType : PayStatus.values()) {
            if (payType.getCode().equals(code)) {
                return payType;
            }
        }
        return null;
    }
    PayStatus(String code, String chnName) {
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
