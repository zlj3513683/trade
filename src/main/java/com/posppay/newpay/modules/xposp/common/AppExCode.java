package com.posppay.newpay.modules.xposp.common;


/**
 * @author YangCH
 * create on 2018/11/6
 */
public class AppExCode {
    @ExceptionAnno(
            declare = "成功"
    )
    public static final String SUCCESS = "0000";
    public static final String IDGEN_MAXLMT_REACHED = "0001";
    public static final String FORMATTER_DEFAULT_ERR = "0002";
    public static final String FILEPARSER_DEFAULT_ERR = "0003";
    public static final String FILE_NOT_FOUND = "0004";
    public static final String SERIALIZE_OR_UNSERIALIZE_FAILED = "0005";
    public static final String CREATE_SSLCONTEXT_FAILED = "0006";
    public static final String STREAM_CLOSE_ERROR = "0007";
    @ExceptionAnno(
            declare = "渠道系统异常，请稍后重试"
    )
    public static final String NETWORK_CONNECTION_FAILED = "0008";
    public static final String ILLEGAL_SENTINELS_LIST = "0009";
    public static final String ILLEGAL_MASTERNAME = "0010";
    public static final String UNSUPPORT_SERIALIZE_SESSIONTYPE = "0011";
    @ExceptionAnno(
            declare = "渠道系统异常，请稍后重试"
    )
    public static final String SOCKET_NETWORK_CONNECTION_FAILED = "0012";

    @ExceptionAnno(
            declare = "参数不正确，请联系客户经理重置参数。",
            tip = "错误的状态参数，一般是非法数据"
    )
    public static final String PARAMS_ILLEGAL = "1001";

    @ExceptionAnno(
            declare = "参数不完整，请联系客户经理重置参数。",
            tip = "请求的参数里有空值"
    )
    public static final String PARAMS_NULL = "1002";
    @ExceptionAnno(
            declare = "订单号重复",
            tip = "商户系统订单号重复"
    )
    public static final String ORDER_EXITS = "1020";
    @ExceptionAnno(
            declare = "交易类型错误",
            tip = "交易类型错误"
    )
    public static final String TYPE_ERR = "1044";

    @ExceptionAnno(
            declare = "商户信息未生效，请联系客户经理。",
            tip = "商户信息不存在"
    )
    public static final String MERCHANT_NOT_AVAILABLE = "1003";

    @ExceptionAnno(
            declare = "门店信息未生效，请联系客户经理。",
            tip = "门店信息不存在"
    )
    public static final String STORE_NOT_AVAILABLE = "1012";

    @ExceptionAnno(
            declare = "原交易状态异常，如：已撤销、已退货。",
            tip = "订单状态非法"
    )
    public static final String ORDER_STATUS_ILLEGAL = "1004";
    @ExceptionAnno(
            declare = "原订单不存在，请核实。",
            tip = "订单不存在"
    )
    public static final String ORDER_NOT_FOUND = "1005";
    @ExceptionAnno(
            declare = "原交易流水不存在，请核实。",
            tip = "流水不存在"
    )
    public static final String TRADE_NOT_EXIST = "1006";
    @ExceptionAnno(
            declare = "交易正在处理中",
            tip = "交易正在处理中"
    )
    public static final String TRADE_EXIST = "1007";
    @ExceptionAnno(
            declare = "只能撤销当日交易。",
            tip = "非当日交易不能撤销"
    )
    public static final String NOT_ORIGINAL_TRANS_DATE = "1008";
    @ExceptionAnno(
            declare = "操作超时,请重试",
            tip = "session过期"
    )
    public static final String SESSION_EXIPRE = "1009";
    @ExceptionAnno(
            declare = "MAC错误，请签到后重试。",
            tip = "设备工作密钥有误"
    )
    public static final String CHECK_MAC_ERROR = "1011";
    @ExceptionAnno(
            declare = "第三方秘钥信息校验出错,请联系客户经理",
            tip = "第三方秘钥信息校验出错"
    )
    public static final String CHN_CHECK_MAC_ERROR = "9022";
    @ExceptionAnno(
            declare = "结算信息有误"
    )
    public static final String MSTL_NOT_EXITS = "1101";
    @ExceptionAnno(
            declare = "系统商户信息不存在"
    )
    public static final String MERC_NOT_EXITS = "1201";

    @ExceptionAnno(
            declare = "商户已停用"
    )
    public static final String MERC_IS_STOP = "1202";
    @ExceptionAnno(
            declare = "该商户不存在扫码产品"
    )
    public static final String MERC_NOT_SCAN = "1203";
    @ExceptionAnno(
            declare = "该商户不属于这个机构,请联系客服经理"
    )
    public static final String MERC_INST_ERR = "1204";
    @ExceptionAnno(
            declare = "门店信息不存在",
            tip = "门店信息不存在"
    )
    public static final String STOE_NOT_EXIST = "1301";
    @ExceptionAnno(
            declare = "门店已关闭",
            tip = "门店已关闭"
    )
    public static final String STOE_IS_CLOSED = "1302";
    @ExceptionAnno(
            declare = "终端不存在",
            tip = "非法终端"
    )
    public static final String DEVICE_NOT_EXIST = "1401";
    @ExceptionAnno(
            declare = "设备重复绑定，请核实。",
            tip = "设备要绑定商终信息对应设备已经存在，无法绑定"
    )
    public static final String DEVICE_EXIST = "1402";
    @ExceptionAnno(
            declare = "终端号非法",
            tip = "终端号非法"
    )
    public static final String DEVICE_NOT_ACTIVED = "1403";
    @ExceptionAnno(
            declare = "无工作密钥，请签到后重试",
            tip = "设备无工作密钥"
    )
    public static final String DEVICE_WORK_KEY_NOT_EXIST = "1404";
    @ExceptionAnno(
            declare = "应用参数不完整，请签到后重试。",
            tip = "设备参数不存在"
    )
    public static final String DEVICE_PARAM_NOT_EXIST = "1405";
    @ExceptionAnno(
            declare = "班次号不正确，请核实。",
            tip = "未找到设备班次"
    )
    public static final String DEVICE_PARAM_FLOW_NOT_EXIST = "1406";
    @ExceptionAnno(
            declare = "风险冻结",
            tip = "风险冻结"
    )
    public static final String DEVICE_FREEZE = "1407";
    @ExceptionAnno(
            declare = "商户信息未生效，请联系客户经理。",
            tip = "商终信息不存在"
    )
    public static final String MERCHANT_NOT_EXIST = "1501";

    @ExceptionAnno(
            declare = "应用未激活，请联系客户经理。",
            tip = "商终未激活"
    )
    public static final String TERMINAL_NOT_ACTIVE = "1502";
    @ExceptionAnno(
            declare = "无工作密钥，请签到后重试。",
            tip = "商终无工作密钥"
    )
    public static final String TERMINAL_WORK_KEY_NOT_EXIST = "1503";
    @ExceptionAnno(
            declare = "交易成功"
    )
    public static final String ISO_RESP_00 = "2000";
    @ExceptionAnno(
            declare = "请持卡人与发卡银行联系"
    )
    public static final String ISO_RESP_01 = "2001";
    @ExceptionAnno(
            declare = "请持卡人与发卡银行联系"
    )
    public static final String ISO_RESP_02 = "2002";
    @ExceptionAnno(
            declare = "无效商户"
    )
    public static final String ISO_RESP_03 = "2003";
    @ExceptionAnno(
            declare = "此卡为无效卡"
    )
    public static final String ISO_RESP_04 = "2004";
    @ExceptionAnno(
            declare = "持卡人认证失败"
    )
    public static final String ISO_RESP_05 = "2005";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_06 = "2006";
    @ExceptionAnno(
            declare = "没收卡，请联系收单行"
    )
    public static final String ISO_RESP_07 = "2007";
    @ExceptionAnno(
            declare = "交易失败，请重试"
    )
    public static final String ISO_RESP_09 = "2009";
    @ExceptionAnno(
            declare = "无效交易"
    )
    public static final String ISO_RESP_12 = "2012";
    @ExceptionAnno(
            declare = "无效金额"
    )
    public static final String ISO_RESP_13 = "2013";
    @ExceptionAnno(
            declare = "无效卡号"
    )
    public static final String ISO_RESP_14 = "2014";
    @ExceptionAnno(
            declare = "此卡不能受理"
    )
    public static final String ISO_RESP_15 = "2015";
    @ExceptionAnno(
            declare = "无此发卡方，请咨询发卡行"
    )
    public static final String ISO_RESP_16 = "2016";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_19 = "2019";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_20 = "2020";
    @ExceptionAnno(
            declare = "操作有误，请重试"
    )
    public static final String ISO_RESP_22 = "2022";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_23 = "2023";
    @ExceptionAnno(
            declare = "没有原始交易，请联系发卡方"
    )
    public static final String ISO_RESP_25 = "2025";
    @ExceptionAnno(
            declare = "交易失败，请重试"
    )
    public static final String ISO_RESP_30 = "2030";
    @ExceptionAnno(
            declare = "此卡不能受理"
    )
    public static final String ISO_RESP_31 = "2031";
    @ExceptionAnno(
            declare = "过期的卡"
    )
    public static final String ISO_RESP_33 = "2033";
    @ExceptionAnno(
            declare = "作弊卡，呑卡"
    )
    public static final String ISO_RESP_34 = "2034";
    @ExceptionAnno(
            declare = "有作弊嫌疑的卡"
    )
    public static final String ISO_RESP_35 = "2035";
    @ExceptionAnno(
            declare = "受限制的卡"
    )
    public static final String ISO_RESP_36 = "2036";
    @ExceptionAnno(
            declare = "没收卡，请联系收单行"
    )
    public static final String ISO_RESP_37 = "2037";
    @ExceptionAnno(
            declare = "密码错误次数超限，请与发卡方联系"
    )
    public static final String ISO_RESP_38 = "2038";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_39 = "2039";
    @ExceptionAnno(
            declare = "请求的功能尚不支持"
    )
    public static final String ISO_RESP_40 = "2040";
    @ExceptionAnno(
            declare = "挂失卡"
    )
    public static final String ISO_RESP_41 = "2041";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_42 = "2042";
    @ExceptionAnno(
            declare = "被窃卡"
    )
    public static final String ISO_RESP_43 = "2043";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_44 = "2044";
    @ExceptionAnno(
            declare = "可用余额不足"
    )
    public static final String ISO_RESP_51 = "2051";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_52 = "2052";
    @ExceptionAnno(
            declare = "该卡已过期"
    )
    public static final String ISO_RESP_54 = "2054";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_56 = "2056";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_57 = "2057";
    @ExceptionAnno(
            declare = "发卡方不允许该卡在本终端进行此交易"
    )
    public static final String ISO_RESP_58 = "2058";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_59 = "2059";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_60 = "2060";
    @ExceptionAnno(
            declare = "交易金额超限"
    )
    public static final String ISO_RESP_61 = "2061";
    @ExceptionAnno(
            declare = "受限制的卡"
    )
    public static final String ISO_RESP_62 = "2062";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_63 = "2063";
    @ExceptionAnno(
            declare = "交易金额与原交易不匹配"
    )
    public static final String ISO_RESP_64 = "2064";
    @ExceptionAnno(
            declare = "超出取款次数限制"
    )
    public static final String ISO_RESP_65 = "2065";
    @ExceptionAnno(
            declare = "交易失败，请联系收单行或银联"
    )
    public static final String ISO_RESP_66 = "2066";
    @ExceptionAnno(
            declare = "没收卡"
    )
    public static final String ISO_RESP_67 = "2067";
    @ExceptionAnno(
            declare = "交易超时，请重试"
    )
    public static final String ISO_RESP_68 = "2068";
    @ExceptionAnno(
            declare = "密码错误次数超限"
    )
    public static final String ISO_RESP_75 = "2075";
    @ExceptionAnno(
            declare = "请向网络中心签到"
    )
    public static final String ISO_RESP_77 = "2077";
    @ExceptionAnno(
            declare = "POS终端重传脱机数据"
    )
    public static final String ISO_RESP_79 = "2079";
    @ExceptionAnno(
            declare = "系统日切，请稍后重试"
    )
    public static final String ISO_RESP_90 = "2090";
    @ExceptionAnno(
            declare = "交易失败，请稍后重试"
    )
    public static final String ISO_RESP_91 = "2091";
    @ExceptionAnno(
            declare = "交易失败，请稍后重试"
    )
    public static final String ISO_RESP_92 = "2092";
    @ExceptionAnno(
            declare = "交易失败，请联系发卡行"
    )
    public static final String ISO_RESP_93 = "2093";
    @ExceptionAnno(
            declare = "拒绝，重复交易，请稍后重试"
    )
    public static final String ISO_REPEAT_DEAL = "2094";
    @ExceptionAnno(
            declare = "交易失败，请稍后重试"
    )
    public static final String ISO_RESP_95 = "2095";
    @ExceptionAnno(
            declare = "拒绝，交换中心异常，请稍后重试"
    )
    public static final String ISO_RESP_96 = "2096";
    @ExceptionAnno(
            declare = "终端号未登记"
    )
    public static final String ISO_RESP_97 = "2097";
    @ExceptionAnno(
            declare = "发卡方超时"
    )
    public static final String ISO_RESP_98 = "2098";
    @ExceptionAnno(
            declare = "PIN格式错，请重新签到"
    )
    public static final String ISO_RESP_99 = "2099";
    @ExceptionAnno(
            declare = "MAC校验错，请重新签到"
    )
    public static final String ISO_RESP_A0 = "A0";
    @ExceptionAnno(
            declare = "转账货币不一致"
    )
    public static final String ISO_RESP_A1 = "20A1";
    @ExceptionAnno(
            declare = "交易成功，请向资金转入行确认"
    )
    public static final String ISO_RESP_A2 = "20A2";
    @ExceptionAnno(
            declare = "资金到账行账号不正确"
    )
    public static final String ISO_RESP_A3 = "20A3";
    @ExceptionAnno(
            declare = "交易成功，请向资金到账行确认"
    )
    public static final String ISO_RESP_A4 = "20A4";
    @ExceptionAnno(
            declare = "交易成功，请向资金到账行确认"
    )
    public static final String ISO_RESP_A5 = "20A5";
    @ExceptionAnno(
            declare = "交易成功，请向资金到账行确认"
    )
    public static final String ISO_RESP_A6 = "20A6";
    @ExceptionAnno(
            declare = "安全处理失败"
    )
    public static final String ISO_RESP_A7 = "20A7";
    @ExceptionAnno(
            declare = "渠道签到失败，请签到后重试。",
            tip = "渠道签到失败"
    )
    public static final String ISO_SIGNUP_FAILED = "2101";
    @ExceptionAnno(
            declare = "原交易信息不匹配，交易失败!"
    )
    public static final String ISO_RESPONSE_UNSAFE = "2102";
    @ExceptionAnno(
            declare = "渠道信息不存在（TSS），请联系客户经理。",
            tip = "通道不存在"
    )
    public static final String CHANNEL_NOT_EXIST = "2301";
    @ExceptionAnno(
            declare = "渠道流水不存在，请核实。",
            tip = "通道流水不存在"
    )
    public static final String CHANNEL_TRANS_NOT_EXIST = "2302";
    @ExceptionAnno(
            declare = "渠道交易不支持",
            tip = "通道交易不支持，比如T0通道不支持撤销等"
    )
    public static final String CHANNEL_NOT_SUPPORTED_OPERATION = "2303";
    @ExceptionAnno(
            declare = "卡号与原交易不符",
            tip = "通道交易与原始卡号不一致"
    )
    public static final String CHANNEL_CARDNO_ILLEGAL = "2304";


    @ExceptionAnno(
            declare = "渠道商户信息未生效",
            tip = "渠道商户信息未生效"
    )
    public static final String CHANNELMRCH_NOT_AVAILABLE = "3001";
    @ExceptionAnno(
            declare = "渠道终端信息未生效",
            tip = "渠道终端信息未生效"
    )
    public static final String CHANNELTRMNL_NOT_AVAILABLE = "3002";

    @ExceptionAnno(
            declare = "该设备已绑定",
            tip = "终端已经被绑定了，无法重启绑定"
    )
    public static final String TSS_TERMINAL_ALREADY_BIND = "6001";
    @ExceptionAnno(
            declare = "绑定关系异常，解绑失败，请联系智能终端项目组。",
            tip = "请求设备SN与原始数据不一致，解绑处理"
    )
    public static final String TSS_TERMINAL_NOT_BIND = "6002";
    @ExceptionAnno(
            declare = "无权解绑。",
            tip = "请求机构号与原始数据不一致，解绑处理"
    )
    public static final String TSS_ILLEGAL_BIND = "6003";
    @ExceptionAnno(
            declare = "该设备秘钥未同步，请联系客服"
    )
    public static final String TSS_DEVICEKEY_NOT_EXIST = "6004";
    @ExceptionAnno(
            declare = "不合法的秘钥数据，请联系客服"
    )
    public static final String TSS_ILLEGAL_KEYINFO = "6005";
    @ExceptionAnno(
            declare = "该终端秘钥未同步，请联系客服"
    )
    public static final String TSS_TERMINALKEY_NOT_EXIST = "6006";
    @ExceptionAnno(
            declare = "非法的机构号"
    )
    public static final String TSS_ILLEGAL_INSTNO = "6007";
    @ExceptionAnno(
            declare = "亲，请稍后重试哦",
            tip = "渠道交易达到限流值"
    )
    public static final String SYS_APP_LIMIT = "999A";
    @ExceptionAnno(
            declare = "亲，请稍后重试哦",
            tip = "触发渠道熔断，当前渠道无法交易"
    )
    public static final String SYS_APP_FUSING = "999B";
    @ExceptionAnno(
            declare = "请稍后重试",
            tip = "连接后线库超时"
    )
    public static final String NETWORK_CONNECTION_FAILED_DATA = "9990";
    @ExceptionAnno(
            declare = "请换卡重试",
            tip = "该卡存在安全隐患，请换卡"
    )
    public static final String CARDBIN_NOTFOUND = "4001";

    @ExceptionAnno(
            declare = "交易失败，保证金余额不足",
            tip = "该卡保证金金额已达到阈值"
    )
    public static final String MULTI_BALANCE_NOT_ENOUGH = "4002";

    @ExceptionAnno(
            declare = "交易失败，更新签到信息时报",
            tip = "更新签到信息有误"
    )
    public static final String UPDATE_SIGNUP_ERR = "4003";

    @ExceptionAnno(
            declare = "交易失败，新增签到信息时报",
            tip = "新增签到信息有误"
    )
    public static final String ADD_SIGNUP_ERR = "4004";

    @ExceptionAnno(
            declare = "交易失败，尚未开通此商户",
            tip = "商户费率有误"

    )
    public static final String MERCHANT_RATE_ERR = "5001";

    @ExceptionAnno(
            declare = "交易失败，尚未开通此渠道",
            tip = "渠道费率有误"
    )
    public static final String CHANNEL_RATE_ERR = "5002";

    /**
     * 已经达到下限
     */
    @ExceptionAnno(
            declare = "交易失败，机构异常",
            tip = "机构异常"
    )
    public static final String PAY_ALARM_ERR = "5003";

    @ExceptionAnno(
            declare = "交易失败，尚未激活此渠道费率",
            tip = "渠道费率未激活"
    )
    public static final String CHANNEL_RATE_NOT_ACTIVE = "5004";
    @ExceptionAnno(
            declare = "交易失败，当前不支持这种类型的同步",
            tip = "当前不支持非消费的同步"
    )
    public static final String REVERSAL_NOT_SUPPORT = "5005";
    @ExceptionAnno(
            declare = "交易失败，当前卡尚未激活",
            tip = "当前卡未被激活"
    )
    public static final String CARD_NOT_ACTIVE = "5006";

    /**
     * 以下为加密机相关的错误码
     */
    @ExceptionAnno(
            declare = "输入密钥奇偶校验错误警告",
            tip = "加密机内部错误"
    )
    public static final String HSM_CONFIRM_ERR = "301";
    @ExceptionAnno(
            declare = "密钥长度错误",
            tip = "加密机内部错误"
    )
    public static final String HSM_LENTH_ERR = "302";

    @ExceptionAnno(
            declare = "无效密钥类型",
            tip = "加密机内部错误"
    )
    public static final String HSM_TYPE_ERR = "304";
    @ExceptionAnno(
            declare = "无效密钥长度标识",
            tip = "加密机内部错误"
    )
    public static final String HSM_LEN_FLAG_ERR = "305";
    @ExceptionAnno(
            declare = "源密钥奇偶校验错",
            tip = "加密机内部错误"
    )
    public static final String HSM_KEY_ERR = "310";
    @ExceptionAnno(
            declare = "目的密钥奇偶校验错",
            tip = "加密机内部错误"
    )
    public static final String HSM_KEY_RESULT_ERR = "311";
    @ExceptionAnno(
            declare = "用户存储区域的内容无效",
            tip = "加密机内部错误"
    )
    public static final String HSM_USE_SPACE_ERR = "312";
    @ExceptionAnno(
            declare = "主密钥奇偶校验错",
            tip = "加密机内部错误"
    )
    public static final String HSM_MKY_ERR = "313";
    @ExceptionAnno(
            declare = "LMK对02-03加密下的PIN失效",
            tip = "加密机内部错误"
    )
    public static final String HSM_PKEY_ERR = "314";


    @ExceptionAnno(
            declare = "原交易已全额退货或者已撤销",
            tip = "可退余额非法"
    )
    public static final String ORDER_REFUNDBALANCE_LIMIT = "7001";

    @ExceptionAnno(
            declare = "退货金额超限",
            tip = "可退余额超限"
    )
    public static final String ORDER_REFUND_LIMIT = "7002";
    @ExceptionAnno(
            declare = "该机构短信未配置,请联系客户经理配置短信",
            tip = "短信参数未配置"
    )

    public static final String RISK_NOT_CONFIG = "7004";
    @ExceptionAnno(
            declare = "原交易未成功"
    )
    public static final String ERRSTATUS = "1010";
    @ExceptionAnno(
            declare = "该渠道尚未开通"
    )
    public static final String CHNNOTAPPLY = "1090";
    @ExceptionAnno(
            declare = "当前商户存在异常，请联系客户经理"
    )
    public static final String STOENOTALLOW = "1092";


    @ExceptionAnno(
            declare = "系统繁忙，请重试"
    )
    public static final String UNKNOWN = "9999";
    @ExceptionAnno(
            declare = "退款金额有误"
    )
    public static final String REFUND_NOT_ALLOW = "1099";
    @ExceptionAnno(
            declare = "商户未指定交易渠道,请联系客户经理"
    )
    public static final String MERC_NO_CHANNEL = "1199";
    @ExceptionAnno(
            declare = "机构信息有误，请联系客户经理"
    )
    public static final String ORG_ERR = "1198";
    @ExceptionAnno(
            declare = "通道信息有误，请联系客户经理"
    )
    public static final String CHN_ERR = "1197";
    @ExceptionAnno(
            declare = "通道扫码信息有误，请联系客户经理"
    )
    public static final String CHN_SUPPORT_ERR = "1200";
    @ExceptionAnno(
            declare = "当前付款码类型不支持，请联系客户经理"
    )
    public static final String SCAN_SUPPORT_ERR = "1201";
    @ExceptionAnno(
            declare = "付款码不允许为空"
    )
    public static final String AUTHCODE_ERR = "1196";
    @ExceptionAnno(
            declare = "付款码类型不允许为空"
    )
    public static final String PAYCHANNEL_ERR = "1196";
    @ExceptionAnno(
            declare = "付款码类型未知"
    )
    public static final String NO_SUUPEROT_SCAN = "1205";
    @ExceptionAnno(
            declare = "不支持的交易"
    )
    public static final String UNKNOWN_PAYCHANNEL = "1195";

    public AppExCode() {
    }
}
