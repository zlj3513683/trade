package com.posppay.newpay.modules.sdk.pingan.constant;

/**
 * 交易码和交易信息
 * @author MaoNing
 * @date 2019/4/8
 */
public class ResponseCodeConstant {
    /**交易成功*/
    public static final String SUCCESS_CODE = "CUP00000";
    public static final String SUCCESS_MSG = "交易成功";

    /**通用错误返回信息码*/
    public static final String DEFAULT_ERROR_CODE = "CUP00100";

    /**公用错误信息*/
    public static final String REQUEST_ERROR_MSG = "请求失败!";
    public static final String INVALID_REQUEST = "非法请求";
    public static final String INVALID_DATA = "非法数据";
    public static final String REQUEST_NULL_MSG = "请求数据缺少必要参数";
    public static final String RESPONSE_NULL_MSG = "请求响应无数据";
    public static final String NULL_MSG = "缺少必要商户信息";
    public static final String MERCHANT_FINISH = "进件已完成，不能重复提交";
    public static final String EXAMINE_PASSED = "审核已通过";
    public static final String EXAMINE_NOT_PASSED = "审核不通过!";
    public static final String MERCHANT_WAIT_SUBMIT = "进件已提交，请耐心等待审核";
    public static final String MERCHANT_WAIT_EXAMINE = "正在审核，请耐心等待";
    public static final String MERCHANT_UPDATE_WAIT_SUBMIT = "修改信息已提交，请耐心等待审核";
    public static final String MERCHANT_NOT_SUBMIT = "该商户还未提交信息";
    public static final String ADD_MERCHANT_SUCCESS = "进件成功";
    public static final String ADD_MERCHANT_FAILED = "进件失败";
    public static final String MERCHANT_NULL = "商户不存在";
    public static final String STORE_NULL = "商户下没有门店信息";
    public static final String AREA_NULL = "区域编码不存在";
    public static final String CITY_NULL = "未查找到指定城市";
    public static final String PROVINCE_NULL = "未查找到省份";
    public static final String MCC_NULL = "商户下没有门店信息";
    public static final String SCAN_PRODUCT_NULL = "未查找到扫码产品";
    public static final String BANK_ACCOUNT_NULL = "未查找到银行卡信息";
    public static final String BRANCH_BANK_NULL = "缺少银行卡信息-联行号";
    public static final String CUP_BRANCH_BANK_NULL = "银联联行号查询失败";
    public static final String FEE_RATE_NULL = "未查找到费率信息";
    public static final String INDENT_PHOTO_NULL = "缺少身份证正反面照";
    public static final String SECRET_KEY_NULL = "未录入商户秘钥";
    public static final String INVALID_ORDER = "非法订单";
    public static final String RESPONSE_SIGN_MESSAGE = "响应数据签名校验失败!";

}
