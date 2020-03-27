package com.posppay.newpay.modules.sdk.pingan.constant;

/**
 * @author MaoNing
 * @date 2019/4/2
 * 银联常量
 */
public class PinganConstant {

    /**方法执行成功*/
    public static final String METHOD_SUCCESS = "success";
    /**方法执行失败*/
    public static final String METHOD_FAILED = "failed";
    /*平安发送超时*/
    public static final String PA_PREPAY_CODE = "U";
    /*平安发送超时*/
    public static final String PA_NETWORK_CODE = "T";
    /**平安失败返回*/
    public static final String PA_FAILED_CODE = "F";
    /**平安被冲正*/
    public static final String PA_IS_REALVER = "C";
    /**平安被冲正*/
    public static final String PA_NOT_NEED_CODE = "N";
    /**平安没返回*/
    public static final String PA_UNKNOWN_CODE = "K";
    /**平安发送失败*/
    public static final String PINGAN_WAIT_PAY_CODE = "X";
    /**平安交易成功支付标志*/
    public static final String PA_SUCCESS_PAY_CODE = "S";
    /**默认字符集*/
    public static final String DEFAULT_CHARSET = "UTF-8";
    /**默认版本号*/
    public static final String DEFAULT_VERSION = "2.0";
    /**银联渠道编码*/
    public static final String CHANNEL_CODE = "CUP";
    /**平台机构号*/
    public static final String PARTNER = "401580000261";
    /**银联商户数据请求地址*/
    public static final String SEND_URL = "https://up.95516.com/interface/gateway";
    /**银联交易数据请求地址*/
    public static final String PAYMENT_URL = "https://qra.95516.com/pay/gateway";
    /**银联通知请求地址*/
    public static final String NOTIFY_URL = "https://pay.wisdomsaas.com/notice/cup/native";
    /**签名类型*/
    public static final String SIGN_TYPE = "MD5";
    /**参数数据类型*/
    public static final String DATA_TYPE = "xml";
    /**秘钥*/
    public static final String SECURITY_KEY = "db1714406a0e783ea408df7c255885bc";
    /**图片路径*/
    public static final String  PIC_PATH = "/home/mcadm/";
    /**扫码产品编号*/
    public static final String SCAN_PRODUCT_CODE = "1002";
    /**币种*/
    public static final String FEE_TYPE = "CNY";
    /**进件邮箱*/
    public static final String DEFAULT_EMAIL = "13767010636@163.com";
    /**服务器ip*/
    public static final String MACHINE_IP = "139.224.11.41";
    /**不发送短信、不发送邮件*/
    public static final Integer INTEGER_ZERO = 0;
    /**证件类型:身份证、发送短信、发送邮件*/
    public static final Integer INTEGER_ONE = 1;
    /**数字2*/
    public static final Integer INTEGER_TWO = 2;
    /**费率单位转换10*/
    public static final Integer INTEGER_TEN = 10;
    /**
     * 交易通讯成功状态status 0代表成功
     */
    public static final String COMMUNICATE_SUCCESS = "0";
    /**
     * 业务接收状态成功为0
     */
    public static final String BUS_SUCCESS = "0";
    /** 银行账户对公、进件完成状态、银联进件审核状态：审核通过*/
    public static final String STRING_ONE = "1";
    /**进件待审核、银联进件审核状态：审核不通过*/
    public static final String STRING_TWO = "2";
    /**商户修改状态*/
    public static final String STRING_THREE = "3";
    /**数字0*/
    public static final int ZERO = 0;
    /**日期格式*/
    public static final String DATE_FORMAT = "YYYYMMDDHHMMSS";


    /**交易状态*/
    public static final String TRADE_STATUS_SUCCESS = "SUCCESS";
    public static final String TRADE_STATUS_NOT_PAY = "NOTPAY";
    public static final String TRADE_STATUS_USER_PAYING = "USERPAYING";
    public static final String TRADE_STATUS_PROCESSING = "PROCESSING";


    /**
     * 请求服务名
     */
    public static final class ServerName{
        /**普通商户进件*/
        public static final String NORMAL_MCH_ADD = "normal_mch_add";
        /**图片上传*/
        public static final String PIC_UPLOAD = "pic_upload";
        /**商户进件结果查询*/
        public static final String MCH_INFO_SEARCH = "mch_info_search";
        /**商户信息查询*/
        public static final String MCH_INFO_BATCH_SEARCH = "mch_info_batch_search";
        /**商户报备状态查询*/
        public static final String MCH_THI_REGISTER_SEARCH = "mch_thi_register_search";
        /**B扫C交易请求*/
        public static final String UNIFIED_TRADE_MICROPAY = "unified.trade.micropay";
        /**B扫C交易结果查询*/
        public static final String UNIFIED_TRADE_QUERY = "unified.trade.query";
        /**B扫C交易撤销*/
        public static final String UNIFIED_MICROPAY_REVERSE = "unified.micropay.reverse";
        /**B扫C交易退款申请*/
        public static final String UNIFIED_TRADE_REFUND = "unified.trade.refund";
        /**B扫C交易退款结果查询*/
        public static final String UNIFIED_TRADE_REFUND_QUERY = "unified.trade.refundquery";
        /**C扫B交易请求*/
        public static final String UNIFIED_TRADE_NATIVE = "unified.trade.native";
    }

    /**
     * 银联扫码支付类型
     */
    public static final class PayTypeCenter{
        /**微信公众号支付*/
        public static final Integer WECHAT_PUBLIC = 10000959;
        /**微信付款码支付*/
        public static final Integer WECHAT_PAYMENT_CODE = 10000960;
        /**微信扫码支付*/
        public static final Integer WECHAT_SCAN_CODE = 10000961;
        /**支付宝服务窗支付*/
        public static final Integer ALIPAY_SERVICE_WINDOW = 10000970;
        /**支付宝付款码支付*/
        public static final Integer ALIPAY_PAYMENT_CODE = 10000971;
        /**支付宝扫码支付*/
        public static final Integer ALIPAY_SCAN_CODE = 10000972;
        /**银联付款码支付*/
        public static final Integer CUP_PAYMENT_CODE = 10000973;
        /**银联扫码支付*/
        public static final Integer CUP_SCAN_CODE = 10000974;
    }
}
