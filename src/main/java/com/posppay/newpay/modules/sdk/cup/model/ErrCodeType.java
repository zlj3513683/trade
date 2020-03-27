package com.posppay.newpay.modules.sdk.cup.model;


/**
 * 功能：错误码转换枚举
 * @author zengjw
 */

public enum ErrCodeType {
    SYSTEMERROR("9001","接口返回错误","SYSTEMERROR","3"),
    INTERNALERROR("9002","接口返回错误","Internal error","3"),
    BANKERROR("9003","银行系统异常","BANKERROR","3"),
    WAITPAY("9004","用户支付中，需要输入密码","10003","3"),
    USERPAYING("9005","用户支付中，需要输入密码","USERPAYING","3"),
    SYSTEMERR("9006","接口返回错误","System error","3"),
    ACQSYSTEMERR("9007","接口返回错误","aop.ACQ.SYSTEM_ERROR","3"),
    ACQSYSERR("9008","接口返回错误","ACQ.SYSTEM_ERROR","3"),
    RULELIMIT("9009","当前交易异常","RULELIMIT","2"),
    TRADEERROR("9010","103 暂无可用的支付方式,请绑定其它银行卡完成支付","TRADE_ERROR","2"),
    PARAMERROR("9011","参数错误","PARAM_ERROR","2"),
    ORDERPAID("9012","参数错误","ORDERPAID","2"),
    NOAUTH("9013","商户无权限","NOAUTH","2"),
    AUTHCODEEXPIRE("9014","二维码已过期，请用户在微信上刷新后再试","AUTHCODEEXPIRE","2"),
    NOTENOUGH("9015","余额不足","NOTENOUGH","2"),
    NOTSUPORTCARD("9016","不支持卡类型","NOTSUPORTCARD","2"),
    ORDERCLOSED("9017","订单已关闭","ORDERCLOSED","2"),
    ORDERREVERSED("9018","订单已撤销","ORDERREVERSED","2"),
    AUTHCODEERR("9019","授权码参数错误","AUTH_CODE_ERROR","2"),
    AUTHCODEINVALID("9020","授权码检验错误","AUTH_CODE_INVALID","2"),
    XMLFORMATERR("9021","格式错误","XML_FORMAT_ERROR","2"),
    SIGNERROR("9022","签名错误","SIGNERROR","2"),
    LACKPARAMS("9023","缺少参数","LACK_PARAMS","2"),
    NOTUTF8("9024","编码格式错误","NOT_UTF8","2"),
    BUYERMISMATCH("9025","支付帐号错误","BUYER_MISMATCH","2"),
    APPIDNOTEXIST("9026","APPID不存在","APPID_NOT_EXIST","2"),
    OUTTRADENOUSED("9027","商户订单号重复","OUT_TRADE_NO_USED","2"),
    APPIDMCHIDNOTMATCH("9028","appid和mch_id不匹配","APPID_MCHID_NOT_MATCH","2"),
    JMPT100027("9029","付款码已扣款","JMPT100027","2"),
    ACQINVALIDPARAMETER("9031","参数无效","ACQ.INVALID_PARAMETER","2"),
    ACQACCESSFORBIDDEN("9032","无权限使用接口","ACQ.ACCESS_FORBIDDEN","2"),
    ACQEXISTFORBIDDENWORD("9033","订单信息中包含违禁词","ACQ.EXIST_FORBIDDEN_WORD","2"),
    ACQTOTALFEEEXCEED("9034","订单总金额超过限额","ACQ.TOTAL_FEE_EXCEED","2"),
    ACQPAYMENTAUTHCODEINVALID("9035","支付授权码无效","ACQ.PAYMENT_AUTH_CODE_INVALID","2"),
    ACQCONTEXTINCONSISTENT("9036","交易信息被篡改","ACQ.CONTEXT_INCONSISTENT","2"),
    AUTHCODINVALID("9037","无效付款码","Auth code invalid","2"),
    ACQBUYERBALANCEERR("9038","买家余额不足","ACQ.BUYER_BALANCE_NOT_ENOUGH","2"),
    ACQBUYERBANKERR("9039","用户银行卡余额不足","ACQ.BUYER_BANKCARD_BALANCE_NOT_ENOUGH","2"),
    BALANCEPAYERR("9040","余额支付功能关闭","ACQ.ERROR_BALANCE_PAYMENT_DISABLE","2"),
    BUYERR("9041","买卖家不能相同","ACQ.ERROR_BALANCE_PAYMENT_DISABLE","2"),
    BUYNOTMERGEERR("9042","交易买家不匹配","ACQ.TRADE_BUYER_NOT_MATCH","2"),
    BUYSTATUSERR("9043","买家状态非法","ACQ.BUYER_ENABLE_STATUS_FORBID","2"),
    CALLTRMNLERR("9044","唤起移动收银台失败","ACQ.PULL_MOBILE_CASHIER_FAIL","2"),
    WIFIPAYERR("9045","用户的无线支付开关关闭","ACQ.MOBILE_PAYMENT_SWITCH_OFF","2"),
    PAYFAIL("9046","支付失败","ACQ.PAYMENT_FAIL","2"),
    LIMITFEE("9047","买家付款日限额超限","ACQ.BUYER_PAYMENT_AMOUNT_DAY_LIMIT_ERROR","2"),
    AMTLIMIT("9048","商户收款额度超限","ACQ.BEYOND_PAY_RESTRICTION","2"),
    AMTLIMITMONTH("9049","商户收款金额超过月限额","ACQ.BEYOND_PER_RECEIPT_RESTRICTION","2"),
    PAYLIMITMONTH("9050","买家付款月额度超限","ACQ.BUYER_PAYMENT_AMOUNT_MONTH_LIMIT_ERROR","2"),
    AUTHENERR("9051","买家未通过人行认证","ACQ.ERROR_BUYER_CERTIFY_LEVEL_LIMIT","2"),
    RISKERR("9052","支付有风险","ACQ.PAYMENT_REQUEST_HAS_RISK","2"),
    TOOLERR("9053","没用可用的支付工具","ACQ.NO_PAYMENT_INSTRUMENTS_AVAILABLE","2"),
    MRCHERR("9054","商户门店编号无效","ACQ.INVALID_STORE_ID","2"),
    AUTHCODERR("9055","无效付款码","Auto code invalid","2"),
    UNKNOWNERR("9056","系统繁忙","aop.unknow-error","3"),
    FLOWNOTFOUND("9057","查询的交易不存在","ACQ.TRADE_NOT_EXIST","2"),
    ORDERNOTFOUND("9058","此交易订单号不存在","ORDERNOTEXIST","2"),
    FLOWINFONOTFOUND("9059","此交易订单号不存在","Order not exists","2"),
    DATANOTFOUND("9060","数据不存在","105","2"),
    REFUNDEXITS("9061","该退款请求已存在","Refund exists","2"),
    REFUNDISEXISTS("9062","退款已存在","REFUNDINFOTEXIST","2"),
    REFUNDAMTERR("9063","退款金额错误","REFUND_FEE_INVALID","2"),
    REFUNDING("9064","该笔交易已转入退款","REFUNDING","3"),
    NOTPAY("9065","用户未支付","NOTPAY","3"),
    REFUNDPROCESSING("9066","退款处理中请耐心等待","PROCESSING","3"),
    REFUNDNOTEXITS("9067","退款不存在","Refund not exists","2"),
    DEFAULTERR("9999","未知错误","DEFAULTERR","2"),
    UNKNOWN("9068","交易状态未明","04","3");



    private String transCode;
    private String chnName;
    private String code;
    private String status;

    /**
     * 功能： 根据code来获取具体的枚举
     * @param code
     * @return
     */
    public static ErrCodeType fromCode(String code){
        for (ErrCodeType payType : ErrCodeType.values()) {
            if (payType.getCode().equals(code)) {
                return payType;
            }
        }
        return DEFAULTERR;
    }
    public static ErrCodeType fromTransCode(String transCode){
        for (ErrCodeType errCodeType : ErrCodeType.values()) {
            if (errCodeType.getTransCode().equals(transCode)) {
                return errCodeType;
            }
        }
        return DEFAULTERR;
    }

    ErrCodeType(String transCode, String chnName, String code, String status) {
        this.transCode = transCode;
        this.chnName=chnName;
        this.code=code;
        this.status=status;
    }

    ErrCodeType() {
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

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
