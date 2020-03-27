package com.posppay.newpay.modules.xposp.common;

import java.math.RoundingMode;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 常量表
 *
 * @author lance
 * <p>
 * 2011-10-28
 */
public class Const {

    public static final class StarPos {
        /**
         * 建链超时时间
         */
        public static final int ISO_CONN_TIMEOUT = 15000;
        /**
         * 处理链路超时时间
         */
        public static final int ISO_READ_TIMEOUT = 45000;
        /**
         * 预付卡消费
         */
        public static final String PROCESS_UP_CARD_CONSUME = "001000";
        public static final String PROCESS_UP_CARD_CANCEL = "201000";
        /**
         * 卡片改密3域
         */
        public static final String PROCESS_UP_CARD_PINRESET = "311000";
        /**
         * 手工退货
         */
        public static final String PROCESS_UP_CARD_MANUL_REFUND = "211000";
        /**
         * 退货
         */
        public static final String PROCESS_UP_CARD_REFUND = "201000";
        public static final String SERVICECODE_UP_CARD_CONSUME = "00";
        /**
         * 53域带主账号加密标识
         */
        public static final String SECURITYINFO_260 = "2600000000000000";
        /**
         * 39域成功应答
         */
        public static final String ISO_RESPCODE_00 = "00";
        /**
         * 受理机构标识
         */
        public static final String ACQUIRING_CODE = "00000000";
        /**
         * 发送机构标识
         */
        public static final String INSTITUTION_CODE = "00000000";
        /**
         * 网络管理代码
         */
        public static final String NETMANAGE = "101";
        /**
         * 激活状态
         */
        public static final int IS_ACTIVE = 1;
        /**
         * 预警状态
         */
        public static final int ALARM_STATUS = 0;
        /**
         * 到达下限
         */
        public static final int LIMIT_STATUS = 3;

        /**
         * 告警状态
         */
        public static final int WARN_STATUS = 2;
        /**
         * 正常状态
         */
        public static final int NORMAL_STATUS = 1;
        /**
         * 余额查询
         */
        public static final String PROCCODE_BALANCE = "301000";
        /**
         * 余额查询25域
         */
        public static final String SERVICE_CODE_BALANCE = "00";
        /**
         * 生成PinKey标识
         */
        public static final String PIN_FLAG = "1";
        /**
         * 生成工作秘钥macKey标识
         */
        public static final String MAC_FLAG = "2";
        /**
         * 预付卡商终类型
         */
        public static final int UPCARD_MRCH_TYPE = 2;
        /**
         * 零元
         */
        public static final String ZERO = "0.00";
        /**
         * 减号
         */
        public static final String MINUS = "-";
        /**
         * 数字2
         */
        public static final int SECOND = 2;

        /**
         * 数字0
         */
        public static final int ZERO_NUM = 0;
        /**
         * 无
         */
        public static final String NON = "";
        /**
         * 逗号
         */
        public static final String COMMA = ",";
        /**
         * 数字1
         */
        public static final int ONE = 1;
        /**
         * 手工退货异常
         */
        public static final String UNKNOWN = "9999";
        /**
         * 秘钥条数2
         */
        public static final int KEY_SIZE_TWO = 2;
        /**
         * 秘钥条数1
         */
        public static final int KEY_SIZE_ONE = 1;
        /**
         * 秘钥条数0
         */
        public static final int KEY_SIZE_ZERO = 0;
        /**
         * 进制转化
         * 费率转化
         */
        public static final int RATE_CONVER = 1000 * 100;
        /**
         * 费率转化一千
         */
        public static final int RATE_THOUNDS = 1000;
        /**
         * 费率单位转化一百
         */
        public static final int RATE_HUNDRED = 100;
        /**
         * 加密机错误标识码
         */
        public static final String HSM_FLAG = "3";
        /**
         *  机构
         */
        public static final int TYPE_ORG = 1;
        /**
         *  渠道
         */
        public static final int TYPE_CHN = 1;
    }


    public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();

    public static final boolean DESMODEL3 = false;

    public static final class DefaultAmountScale {

        public static final RoundingMode AMOUNT_ROUNDINGMODE = RoundingMode.HALF_UP;

        public static final int AMOUNT_SCALE = 2;
    }

    public static final class SystemExecutorConst {
        /**
         * 核心线程数为默认cpu数+1
         */
        public static final int CORE_THREAD_NUMS = CPU_CORES + 1;
        /**
         * 最大线程数为核心线程数*2
         */
        public static final int MAX_THREAD_NUMS = CORE_THREAD_NUMS * 2;
        /**
         * 最大队列数为核心线程数 *
         */
        public static final int QUEUE_LENGTH = CORE_THREAD_NUMS * 6;

        /**
         * 若队列过长，则当前线程内执行
         */
        public static final RejectedExecutionHandler REJECTED_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

        /**
         * 线程销毁时间：60秒
         */
        public static final int KEEPALIVE_SECONDS = 60;

    }

    public static final class OrderStatus {
        /**
         * 0-未支付
         */
        public static final int NOT_PAY = 0;
        public static final String NOT_PAY_STR = "0";
        /**
         * 1-已支付
         */
        public static final int PAYED = 1;
        public static final String PAYED_STR = "1";
        /**
         * 2-已结算
         */
        public static final int SETTLED = 2;
        public static final String SETTLED_STR = "2";
        /**
         * 3-已撤消
         */
        public static final int CANCELED = 3;
        public static final String CANCELED_STR = "3";
        /**
         * 4-部分退款
         */
        public static final int PARTIALLY_REFUNED = 4;
        public static final String PARTIALLY_REFUNED_STR = "4";
        /**
         * 5-全额退款
         */
        public static final int REFUNED = 5;
        public static final String REFUNED_STR = "5";
        /**
         * 6-支付失败
         */
        public static final int PAY_FAILED = 6;
        public static final String PAY_FAILED_STR = "6";
        /**
         * 7-处理中(未知状态)
         */
        public static final int PROCESSING = 7;
        public static final String PROCESSING_STR = "7";
        /**
         * 状态未知
         */
        public static final int UNKNOWN = 8;
        public static final String UNKNOWN_STR = "8";
        /**
         * 状态未知
         */
        public static final int WAIT_PAY = 11;
        public static final String WAIT_PAY_STR = "11";

        /**
         * 9-警告
         */
        public static final int WARN = 9;
        public static final String WARN_STR = "9";

        /**
         * 10-已关闭
         */
        public static final int CLOSED = 10;
        public static final String CLOSED_STR = "10";
    }

    public static final class TransStatus {
        /**
         * 0-未支付/待发起
         */
        public static final int PREPARED = 0;
        public static final String PREPARED_STR = "0";
        /**
         * 1-成功
         */
        public static final int SUCCESS = 1;
        public static final String SUCCESS_STR = "1";
        /**
         * 2-失败
         */
        public static final int FAILED = 2;
        public static final String FAILED_STR = "2";
        /**
         * 3-待确认
         */
        public static final int UNKNOWN = 3;
        public static final String UNKNOWN_STR = "3";
        /**
         * 4-待支付
         */
        public static final int WAIT_PAY = 4;
        public static final String WAIT_PAY_STR = "4";
        /**
         * 4-撤消
         */
        public static final int CANCEL = 4;
        public static final String CANCEL_STR = "4";
        /**
         * 5-部分成功
         */
        public static final int PARTIAL_SUCCESS = 5;
        public static final String PARTIAL_SUCCESS_STR = "5";
        /**
         * 6-部分退货
         */
        public static final int PARTIAL_REFUND = 6;
        public static final String PARTIAL_REFUND_STR = "6";
        /**
         * 7-退货
         */
        public static final int REFUND = 7;
        public static final String REFUND_STR = "7";
        /**
         * 8-待冲正
         */
        public static final int PRE_REVERSAL = 8;
        public static final String PRE_REVERSAL_STR = "8";

        /**
         * 9-警告
         */
        public static final int WARN = 9;
        public static final String WARN_STR = "9";

        /**
         * 10-已关闭
         */
        public static final int CLOSED = 10;
        public static final String CLOSED_STR = "10";
    }

    /**
     * 描述商户的状态
     */
    public static final class MrchStatus {
        public static final int ACTIVATIED = 1;
        public static final int FROZEN = 0;
    }

    /**
     * 订单处理中状态超时时间,3分钟
     **/
    public static final int ORDER_PROCESSING_TIMEOUT = 3 * 60 * 1000;
    /**
     * 工作密钥有效时间
     **/
    public static final int WORKING_KEY_TIMEOUT = 24 * 60 * 60 * 1000;
    /**
     * 交易token超时时间,10分钟
     **/
    public static final int TRANSTOKEN_TIMEOUT_MILLS = 1000 * 60 * 10;


    /**通用有效\无效状态**/
    /**
     * 无效状态
     **/
    public static final int VLDSTATE_INVALID = 0;
    /**
     * 有效状态
     **/
    public static final int VLDSTATE_VALID = 1;
    /**
     * 已上送状态
     **/
    public static final int VLDSTATE_SUBMIT = 2;

    /**
     * 未激活
     **/
    public static final int IS_NOT_ACTIVE = 0;
    /**
     * 已激活
     **/
    public static final int IS_ACTIVE = 1;
    /**
     * 已激活
     **/
    public static final String IS_ACTIVE_STR = "1";

    /**批次状态**/
    /**
     * 等待跑批
     */
    public static final int BATCH_WAITING_RUNNING = 0;

    /**
     * 跑批中
     */
    public static final int BATCH_RUNNING = 1;

    /**
     * 跑批成功
     */
    public static final int BATCH_RUN_OK = 2;
    /**
     * 跑批失败
     */
    public static final int BATCH_RUN_FAIL = 3;
    /**
     * 已下载
     */
    public static final int BATCH_DOWNLOADED = 3;

    /**
     * 卡片类型
     *
     * @author liguolin
     */
    public static final class CardType {
        /**
         * 借记卡
         **/
        public static final String DEBITCARD = "01";
        /**
         * 信用卡
         **/
        public static final String CREDITCARD_02 = "02";
        /**
         * 准贷记卡(贷记卡)
         **/
        public static final String QUASI_CREDITCARD = "03";
        /**
         * 贷记卡
         **/
        public static final String CREDITCARD_01 = "04";
        /**
         * 储蓄卡
         **/
        public static final String DEPOSITCARD = "05";
        /**
         * 双币贷记卡
         **/
        public static final String DOUBLE_CREDITCARD = "06";
        /**
         * 双币借记卡
         **/
        public static final String DOUBLE_DEBITCARD = "07";
        /**
         * 港币贷记卡
         **/
        public static final String HK_CREDITCARD = "08";
        /**
         * 年票卡
         **/
        public static final String ANNUALTICKET_CARD = "09";
        /**
         * 未知卡种
         **/
        public static final String UNKNOWCARD = "99";
        /**
         * 苏宁储值卡
         **/
        public static final String SUNINGPRIPAID_CARD = "10";
    }

    /**
     * 不同终端对应的终端主密钥及其索引值不一样
     **/
    public static final class TerminalType {
        /**
         * 默认终端
         **/
        public static final int NORMAL = 0;
        /**
         * 余额查询专用终端
         **/
        public static final int BALANCE = 99;
    }

    /**
     * 默认流水号
     **/
    public static final String DEFAULT_FLOWNO = "000001";
    /**
     * 默认批次号
     **/
    public static final String DEFAULT_BATCHNO = "000001";

    /**
     * 金融交易响应码
     */
    public static final String ISO_RESPCODE_00 = "00";
    /**
     * 金融交易响应码
     */
    public static final String ISO_RESPCODE_58 = "58";
    /**
     * 金融交易响应码
     */
    public static final String ISO_RESPCODE_94 = "94";
    /**
     * 金融交易响应码
     */
    public static final String ISO_RESPCODE_A0 = "A0";
    /**
     * 金融交易连接超时时间
     */
    public static final int ISO_CONN_TIMEOUT = 15 * 1000;
    /**
     * 金融交易读取超时时间
     */
    public static final int ISO_READ_TIMEOUT = 45 * 1000;

    /**
     * ISO金融交易错误码转本系统错误码开头
     */
    public static final String TP_BEGIN_ISO = "2";
    /**
     * HSM交易错误码转本系统错误码开头
     */
    public static final String TP_BEGIN_HSM = "3";

    /**
     * 状态正常
     */
    public static final int ORG_VALID = 0;
    /**
     * 机构不可用
     */
    public static final int ORG_INVALID = 1;

    /**
     * field#03服务点条件码
     */
    /**
     * 签到
     */
    public static final String PROCCODE_SIGNUP = "910000";
    /**
     * 消费 消费冲正
     */
    public static final String PROCCODE_CONSUME = "000000";
    /**
     * 撤销 撤销冲正
     */
    public static final String PROCCODE_CANCEL = "200000";
    /**
     * 退货（包含联盟积分的退货）
     */
    public static final String PROCCODE_REFUND = "200000";
    /**
     * 预授权/冲正
     */
    public static final String PROCCODE_PRE_AUTH = "030000";
    /**
     *  预授权撤销/冲正
     */
    public static final String PROCCODE_PRE_AUTH_CANCEL = "200000";
    /**
     * 预授权完成（请求）/冲正、预授权完成（通知）
     */
    public static final String PROCCODE_PRE_AUTH_COMPLETE = "000000";
    /**
     *  预授权完成（请求）撤销/冲正
     */
    public static final String PROCCODE_PRE_AUTH_COMPLETE_CANCEL = "200000";
    /**
     * 余额查询
     */
    public static final String PROCCODE_BALANCE = "310000";
    /**
     * 销账处理码
     */
    public static final String PROCCODE_COMFIRM = "290000";
    /**
     * 余额查询
     */
    public static final String PROCCODE_ULINKPLUS_QUERY = "740000";
    /**
     * 余额查询
     */
    public static final String PROCCODE_REALNAMEAUTHENTICATION = "330000";
    /**
     * 电子现金圈存或冲正
     */
    public static final String PROCCODE_CASH_LOAD = "600000";
    /**
     * POS通消费处理码
     */
    public static final String PROCCODE_POST_CONSUME = "190000";
    /**
     * POS通撤销处理码
     */
    public static final String PROCCODE_POST_CANCEL = "280000";
    /**
     * POS通POS交易状态查询
     */
    public static final String PROCCODE_POST_TRANSSTATUS_QUERY = "310000";
    /**
     * POS通担保完成
     */
    public static final String PROCCODE_POST_GUARANTEE_COMPLETE = "970000";
    /**
     * T+0消费处理码
     */
    public static final String PROCCODE_T0_CONSUME = "000000";
    /**
     * T+0交易状态查询处理码
     */
    public static final String PROCCODE_T0_QUERYSTATUS = "740000";
    /**
     * T+0额度查询处理码
     */
    public static final String PROCCODE_T0_LIMIT = "310000";
    /**
     * 全民惠消费处理码
     */
    public static final String PROCCODE_QMH_CONSUME = "270000";
    /**
     * 全民惠积分消费处理码
     */
    public static final String PROCCODE_QMH_POINT_CONSUME = "560000";
    /**
     * 全民惠券码处理码
     */
    public static final String PROCCODE_QMH_TICKET = "360000";
    /**
     * 全民惠权益处理码
     */
    public static final String PROCCODE_QMH_RIGHT = "340000";
    /**
     * 撤销 撤销冲正
     */
    public static final String PROCCODE_QMH_CANCEL = "240000";
    /**
     * 退货
     */
    public static final String PROCCODE_QMH_REFUND = "210000";
    /**
     * 预授权/冲正
     */
    public static final String PROCCODE_QMH_PRE_AUTH = "890000";
    /**
     * 预授权撤销/冲正
     */
    public static final String PROCCODE_QMH_PRE_AUTH_CANCEL = "900000";
    /**
     * 预授权完成（请求）/冲正、预授权完成（通知）
     */
    public static final String PROCCODE_QMH_PRE_AUTH_COMPLETE = "550000";
    /**
     * 预授权完成（请求）撤销/冲正
     */
    public static final String PROCCODE_QMH_PRE_AUTH_COMPLETE_CANCEL = "240000";
    /**
     * 钱包查询
     */
    public static final String PROCCODE_WALLET_QUERY = "310000";
    /**
     * 钱包支付
     */
    public static final String PROCCODE_WALLET_PAY = "000000";
    /**
     * 钱包撤销
     */
    public static final String PROCCODE_WALLET_CANCEL = "200000";
    /**
     * 钱包退货
     */
    public static final String PROCCODE_WALLET_REFUND = "200000";

    /**
     * field#14卡片有效期
     */
    public static final String EXPIRATIONDATE = "3010";

    //field#22 POS输入方式
    /**
     * 手工输入且带PIN
     **/
    public static final String POSENTRYMODE_011 = "011";
    /**
     * 手工输入且不带PIN
     **/
    public static final String POSENTRYMODE_012 = "012";
    /**
     * 磁条读入且带PIN
     **/
    public static final String POSENTRYMODE_021 = "021";
    /**
     * 磁条读入且不带PIN
     **/
    public static final String POSENTRYMODE_022 = "022";

    /**
     * 扫码读入且带PIN
     **/
    public static final String POSENTRYMODE_031 = "031";
    /**
     * 扫码读入且不带PIN
     **/
    public static final String POSENTRYMODE_032 = "032";

    /**
     * IC卡读入且带PIN
     **/
    public static final String POSENTRYMODE_051 = "051";
    /**
     * IC卡读入且不带PIN
     **/
    public static final String POSENTRYMODE_052 = "052";
    /**
     * 快速 PBOC借/贷记IC卡读入（非接触式）且不带PIN
     **/
    public static final String POSENTRYMODE_072 = "072";
    /**
     * 快速 PBOC借/贷记IC卡读入（非接触式）且带PIN
     **/
    public static final String POSENTRYMODE_071 = "071";
    /**
     * POS通固定填920
     **/
    public static final String POST_ENTRYMODE_920 = "920";
    /**
     * T+0额度查询0120
     */
    public static final String T0_ENTRYMODE_0120 = "012";

    /**
     * field#25 服务点条件码
     */
    /**
     * 正常提交
     */
    public static final String SERVICECODE_NORMAL = "00";
    /**
     * /预授权类提交
     */
    public static final String SERVICECODE_PREAUTH = "06";
    /**
     * 电子现金圈存服务点条件码
     */
    public static final String SERVICECODE_CASH_LOAD = "91";
    /**
     * 新一代渠道客户号查询服务点条件码
     */
    public static final String SERVICECODE_NEWGENERATION = "92";
    public static final String SERVICECODE_NEWGENERATION_CONSUME = "81";
    /**
     * POS通服务点条件码
     */
    public static final String SERVICECODE_POST_CONSUME = "82";
    /**
     * POS通服务点条件码
     */
    public static final String SERVICECODE_POST_CANCEL = "81";
    /**
     * POS通服务点条件码
     */
    public static final String SERVICECODE_POST_REFUND = "25";
    /**
     * POS通服务点条件码
     */
    public static final String SERVICECODE_POST_QUERY = "88";
    /**
     * T+0消费服务点条件码
     */
    public static final String SERVICECODE_T0_CONSUME = "00";
    /**
     * t+0交易状态查询服务点条件码
     */
    public static final String SERVICECODE_T0_QUERYSTATUS = "82";
    /**
     * 预授权类提交
     */
    public static final String SERVICECODE_QMH_PREAUTH = "00";
    /**
     * 分期付款
     */
    public static final String SERVICECODE_INSTAL_CONSUME = "64";

    /**
     * field#26 服务点PIN获取码
     **/
    public static final String POINT_OF_SERVICE_PIN_CAPTURE_CODE = "06";
    /**
     * field#26 服务点PIN获取码
     **/
    public static final String QMH_POINT_OF_SERVICE_PIN_CAPTURE_CODE = "12";


    /**
     * field#48 服务点条件码
     **/
    public static final String SD_048_FLAG = "PA";
    public static final String SD_048_TYPE = "Z2";
    public static final String SD_048_END = "#";

    /**
     * field#53 安全控制信息
     */
    public static final String SECURITYINFO_200 = "2000000000000000";

    /**
     * ANSI X9.8 Format（带主账号信息）双倍长密钥算法  磁道不加密
     **/
    public static final String SECURITYINFO_260 = "2600000000000000";
    /**
     * ANSI X9.8 Format（带主账号信息）双倍长密钥算法  磁道加密
     **/
    public static final String SECURITYINFO_261 = "2610000000000000";

    public static final String SECURITYINFO_260_1 = "0600000000000000";

    public static final String SECURITYINFO_260_2 = "0610000000000000";

    /**
     * field#60 自定义域
     * 60.1  消息类型码		    				 N2
     * 60.2  批次号			        		 N6
     * 60.3  网络管理信息码	     				 N3
     * 60.4  终端读取能力	        			 N1
     * 60.5  基于PBOC借/贷记标准的IC卡条件代码		 N1
     * 60.6  支持部分扣款和返回余额标志                                  N1
     * 60.7  账户类型    N3
     */
    /**
     * 管理类交易，脚本通知交易
     **/
    public static final String SD60_1_MSGTYPE_CONTROL = "00";
    /**
     * 查询
     **/
    public static final String SD60_1_MSGTYPE_QUERY = "01";
    /**
     * 积分查询
     **/
    public static final String SD60_1_MSGTYPE_INTEGRAL_QUERY = "03";
    /**
     * 预授权/冲正
     **/
    public static final String SD60_1_MSGTYPE_PRE_AUTH = "10";
    /**
     * 预授权撤销/冲正
     **/
    public static final String SD60_1_MSGTYPE_PRE_AUTH_CANCEL = "11";
    /**
     * 预授权完成（请求）/冲正
     **/
    public static final String SD60_1_MSGTYPE_PRE_AUTH_COMPLETE = "20";
    /**
     * 预授权完成撤销/冲正
     **/
    public static final String SD60_1_MSGTYPE_PRE_AUTH_COMPLETE_CANCEL = "21";
    /**
     * 全民惠预授权完成撤销/冲正
     **/
    public static final String SD60_1_QMH_MSGTYPE_PRE_AUTH_COMPLETE_CANCEL = "23";
    /**
     * 消费/冲正
     **/
    public static final String SD60_1_MSGTYPE_CONSUME = "22";
    /**
     * 消费撤销/冲正
     **/
    public static final String SD60_1_MSGTYPE_CANCEL = "23";
    /**
     * 退货（包含联盟积分的退货）
     **/
    public static final String SD60_1_MSGTYPE_REFUND = "25";

    /**
     * 电子现金指定账户圈存/冲正
     **/
    public static final String SD60_1_MSGTYPE_CASH_LOAD = "40";

    /**
     * 电子现金离线交易
     **/
    public static final String SD60_1_MSGTYPE_CASH_CONSUME = "36";

    public static final String SD62_1_COUPONTYPE_QUERY = "CX";
    public static final String SD62_3_SERVICETYPE_QUERY = "11";

    /**
     * POS终端签到（双倍长密钥算法)
     **/
    public static final String SD60_3_NORMAL = "000";

    /**
     * POS终端签到（DES算法）
     **/
    public static final String SD60_3_SIGNUP_DES = "001";
    /**
     * POS终端签到（双倍长密钥算法)
     **/
    public static final String SD60_3_SIGNUP_3DES1 = "003";
    /**
     * POS终端签到（双倍长密钥算法，含磁道密钥）
     **/
    public static final String SD60_3_SIGNUP_3DES2 = "004";
    /**
     * POS终端批结算/POS终端批上送
     **/
    public static final String SD60_3_SETTLEMENT = "201";
    /**
     * 对账不平衡时，POS终端批上送结束
     **/
    public static final String SD60_3_UPLOADBATCH_INCORRECT_END = "202";
    /**
     * 对账不平衡时，POS终端批上送结束
     **/
    public static final String SD60_3_UPLOADBATCH_CORRECT_END = "207";
    /**
     * POS终端IC卡公钥下载
     **/
    public static final String SD60_3_IC_PUBLICKEY_DOWNLOAD = "370";
    /**
     * POS终端IC卡公钥下载结束
     **/
    public static final String SD60_3_IC_PUBLICKEY_DOWNLOAD_END = "371";
    /**
     * POS终端IC卡公钥信息查询
     **/
    public static final String SD60_3_IC_PUBLICKEY_QUERY = "372";
    /**
     * POS终端IC卡参数下载
     **/
    public static final String SD60_3_IC_PARAM_DOWNLOAD = "380";
    /**
     * POS终端IC卡参数下载结束
     **/
    public static final String SD60_3_IC_PARAM_DOWNLOAD_END = "381";
    /**
     * POS终端IC卡参数信息查询
     **/
    public static final String SD60_3_IC_PARAM_QUERY = "382";
    /**
     * POS终端IC脚本上送
     **/
    public static final String SD60_3_IC_CRIPT_UPLOAD = "951";

    /**
     * 终端读取能力  终端读取能力不可知
     **/
    public static final String SD60_4_TERMINAL_READING_ABILITY_UNKNOW = "0";
    /**
     * 终端读取能力  可读取磁条卡，仅适用于不具备接触式、非接触式界面读取IC卡能力的终端。
     **/
    public static final String SD60_4_TERMINAL_READING_ABILITY_MAGNETICSTRIPE_CARD = "2";
    /**
     * 终端读取能力  可接触式界面读取IC卡。
     **/
    public static final String SD60_4_TERMINAL_READING_ABILITY_CONTACT_ICCARD = "5";
    /**
     * 终端读取能力  可非接触式界面读取IC卡
     **/
    public static final String SD60_4_TERMINAL_READING_ABILITY_CONTACTLESS_ICCARD = "6";


    /**
     * 基于PBOC借/贷记标准的IC卡条件代码    未使用或后续子域存在，或手机芯片交易
     **/
    public static final String SD60_5_IC_SERVICE_CODE_NORMAL = "0";
    public static final String SD60_5_IC_SERVICE_CODE_NORMAL_00 = "00";
    public static final String SD60_5_IC_SERVICE_CODE_NORMAL_01 = "01";
    /**
     * 基于PBOC借/贷记标准的IC卡条件代码   上一笔交易不是IC卡交易或是一笔成功的IC卡交易
     **/
    public static final String SD60_5_IC_SERVICE_CODE_SUCCESS = "1";
    /**
     * 基于PBOC借/贷记标准的IC卡条件代码    上一笔交易虽是IC卡交易但失败
     **/
    public static final String SD60_5_IC_SERVICE_CODE_FAILED = "2";
    /**
     * 自定义 N2 （终端暂时不用，如存在后续子域则默认填‘00’ ）
     **/
    public static final String SD60_5_IC_USELESS = "00";
    public static final String SD60_6_IC_PARTDEBIT_Y = "1";
    public static final String SD60_6_IC_PARTDEBIT_N = "0";
    public static final String SD60_7_ACCOUNTTYPE = "066";

    /**
     * 终端读取能力 N1
     **/
    public static final String SD60_6_TERMINAL_READING_ABILITY_CONTACT_ICCARD = "5";

    /**
     * 基于PBOC借/贷记标准的IC卡条件代码 N1
     **/
    public static final String SD60_7_IC_SERVICE_CODE_NORMAL = "0";

    /**
     * 结算对账应答码
     **/
    public static final String SETTLEMENT_CODE_0 = "0";
    public static final String SETTLEMENT_CODE_1 = "1";
    public static final String SETTLEMENT_CODE_2 = "2";
    public static final String SETTLEMENT_CODE_3 = "3";

    /**
     * field#63 自定义域
     */
    public static final String SD63_1_OPERNO = "001";
    public static final String SD63_1_OPERNO_WECHAT = "01";

    public static final class ResourceConfig {
        public static final long ROOT_RESID = 0;
        public static final String ROOTRESNAME = "资源目录";
    }

    public static final class PermissionConfig {
        /**
         * 功能目录ID
         */
        public static final long ROOTPERID = 0;
        /**
         * 功能目录名称
         */
        public static final String ROOT_PRE_NAME = "功能目录";
        /**
         * 菜单
         */
        public static final String PER_TYPE_MENU = "0";
        /**
         * 按钮
         */
        public static final String PER_TYPE_BUTTON = "1";
        /**
         * 超链接
         */
        public static final String PER_TYPE_HREF = "2";
        /**
         * GRID
         */
        public static final String PER_TYPE_GRID = "3";
        /**
         * 其他
         */
        public static final String PER_TYPE_OTHER = "4";
    }

    public static final class RoleConfig {
        /**
         * 超级系统管理员角色编码
         */
        public static final String SUPER_ADMIN = "SUPER_ADMIN";
        /**
         * 系统管理员登录名
         */
        public static final String SUPER_ADMIN_LOGIN = "admin";
    }

    /**
     * 中间表数据未同步
     */
    public static final int HSM_TMK_NOT_SYNC = 0;

    /**
     * 中间表数据已同步
     */
    public static final int HSM_TMK_SYNC = 1;

    /**
     * 中间表数据异常
     */
    public static final int HSM_TMK_SYNC_ERROR = 2;

    /**
     * 更新中间表秘钥数据
     */
    public static final int HSM_TMK_UPDATE = 5;

    /**
     * 同步终端密钥信息
     */
    public static final int HSM_TMK_SYNC_TRMNL = 1;

    /**
     * 同步设备密钥信息
     */
    public static final int HSM_TMK_SYNC_DEVICE = 9;

    /**
     * tidx字段长度
     */
    public static final int HSM_TMK_SYNC_TIDX_LENGTH = 23;

    /**
     * 实时库保存历史流水的天数
     */
    public static final int ONLINE_KEEP_DAY = 5;

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_PAGESIZE = 10;

    public static final String QUERY_FLOW_DATE_PATTERN = "yyyyMMdd";

    /**
     * 班次号长度
     */
    public static final int DEV_BATCH_NO_LENGTH = 12;
    /**
     * 现金交易默认商户号
     **/
    public static final String CASH_DEFAULT_MRCHNO = "000000000000000";
    /**
     * 现金交易默认商户名称
     **/
    public static final String CASH_DEFAULT_MRCHNAME = "现金交易商户";

    /**
     * 实时库保存流水的天数
     */
    public static final int ONLINE_FLOW_KEEP_DAYS = 4;

    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CHARSET_GBK = "gbk";

    /**
     * xposp日志编号
     */
    public static final String XPOSP_LOG_NO = "xpospLogNo";

    /**
     * xposp日志类型： REQ/RSP
     */
    public static final String XPOSP_LOG_TYPE = "xpospLogType";
    /**
     * xposp日志类型： REQ
     */
    public static final String XPOSP_LOG_TYPE_REQ = "REQ";
    /**
     * xposp日志类型： RSP
     */
    public static final String XPOSP_LOG_TYPE_RSP = "RSP";
    /**
     * 二磁道取卡号条件=
     */
    public static final String CARD_EQ_STR = "=";
    /**
     * 二磁道取卡号条件=
     */
    public static final char CARD_EQ_CHAR = '=';
    /**
     * 二磁道取卡号条件d
     */
    public static final String CARD_D_LOW = "d";
    /**
     * 二磁道获取卡号条件D
     */
    public static final String CARD_D_UPPER = "D";
    /**
     * 42域
     */
    public static final int FIELD_42 = 42;
    /**
     * 数字2
     */
    public static final int SECOND = 2;
    /**
     * mac标识
     */
    public static final int TYPE_MAC_FLAG = 2;
    /**
     * pin秘钥标识
     */
    public static final int TYPE_PIN_FLAG = 1;
    /**
     * mackey的类型
     */
    public static final String MACKEY_FLAG = "2600000000000000";
    /**
     * pinkey的类型
     */
    public static final String PINKEY_FLAG = "1600000000000000";
    /**
     * transMsg的最大长度
     */
    public static final int TRANS_MSG_LEN = 128;
    /**
     * 空格
     */
    public static final String BLANK = " ";
    /**
     * 消费交易消息头
     */
    public static final String CONSUME_MSGTYPE = "0200";
    /**
     * base64加密
     */
    public static final String BASE_64 = "base64";
    /**
     * UTF8编码
     */
    public static final String UTF_8 = "UTF8";
    /**
     * 错误码最大长度
     */
    public static final int TRANSCODE_LEN = 4;
    /**
     * 响应码前缀20
     */
    public static final String TRANSCODE_PRE = "20";
    public static final long EXPIRE_DATE = 7 * 24 * 60 * 60 * 1000;
    /**
     * 日期戳格式
     */
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 参数版本
     */
    public static final String APP_CONFIG = "app";

//    /**
//     * 短信平台相关参数
//     */
//    public static final class SMS_PARAMETER {
//        public static final String PWD = "pwd";
//        public static final String MOBILE = "mobile";
//        public static final String ATTIME = "attime";
//        public static final String ENCODETYPE = "encodeType";
//        public static final String MSGFMT = "msgfmt";
//        public static final String CONTENT = "content";
//        public static final String USERID = "userid";
//        public static final String CONTENT_TYPE = "Content-Type";
//        public static final String APPLICATION_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
//        public static final String NEGATIVE_NUM = "-";
//        public static final int ZERO = 0;
//        public static final int ONE = 1;
//    }

}
