package com.posppay.newpay.common.constant;

/**
 * 常量
 *
 * @author wwa
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 重置后的密码
     */
    public static final String RESET_PW = "88888888";
    /**
     * 初始的代理商角色的名称
     */
    public static final String AGENT_ROLE_NAME = "agent";

    /**
     * 设备是否激活
     */
    public static final class DeviceActive {
        public static final int YES = 1;
        public static final int NO = 0;
    }

    /**
     * sysUserEntity的status 0：禁用 1：启用
     */
    public static final class LoginSysUserStatus {
        public static final int ACTIVE = 1;

        public static final int UNACTIVE = 0;
    }


    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 用户与角色是否为预设值标识
     */
    public class PreInstallSign {

        public static final int YES = 1;

        public static final int NO = 0;
    }

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
     * 商户状态
     *
     * @author lance
     */
    public static final class MrchState {
        /**
         * 商户状态：停用（可以交易但不做资金结算）
         */
        public static final int SUSPEND = 0;
        /**
         * 商户状态：启用
         */
        public static final int ACTIVE = 1;
        /**
         * 商户状态：冻结（停止交易及资金结算）
         */
        public static final int FROZEN = 2;
        /**
         * 商户状态：注销
         */
        public static final int DELETE = 3;
    }

    /**
     * 商户申请状态
     *
     * @author lance
     */
    public static final class MrchApplicationState {
        /**
         * 待提交
         */
        public static final int UNSUMIT = 0;
        /**
         * 待审核
         */
        public static final int WAIT_AUDIT = 1;
        /**
         * 审核中
         */
        public static final int AUDITING = 2;
        /**
         * 审核通过
         */
        public static final int AUDIT_PASS = 3;
        /**
         * 审核不通过（拒绝）
         */
        public static final int AUDIT_REFUSE = 4;
    }

    /**
     * 设备状态（是否激活使用通用有效无效状态判断）
     */
    public static final class DeviceSate {
        /**
         * 设备状态：冻结（不能做交易）
         */
        public static final int FROZEN = 2;
    }

    /**
     * 从posp同步的卡类型 0未知名1借记卡2贷记卡3储值卡4积分卡5预付费卡
     */
    public static final class PospCardType{
        /**
         * 未知名
         */
        public static final int TYPE_UNKNOWN = 0;
        /**
         * 借记卡
         */
        public static final int DEPOSIT_CARD = 1;
        /**
         * 贷记卡
         */
        public static final int CREDIT_CARD = 2;
        /**
         * 储值卡
         */
        public static final int STORED_CARD = 3;
        /**
         * 积分卡
         */
        public static final int INTEGRAL_CARD = 4;
        /**
         * 预付费卡
         */
        public static final int PREPAID_CARD = 5;

        public static final String getCardTypeClear(int accType) {
            if (accType == CREDIT_CARD) {
                return "CREDIT";
            } else {
                return "DEBIT";
            }
        }
    }

    public class AcqCode {
        public static final String DEFAULT_CODE = "0848860000";
        public static final String PREFIX = "084886";
    }

    /**
     * 操作标识
     */
    public static final class ApplicationOpType {
        /**
         * 新增
         */
        public static final String ADD = "I";
        /**
         * 修改
         */
        public static final String UPDATE = "U";
        /**
         * 删除
         */
        public static final String DELETE = "D";
    }

    /**
     * 开户宝收派员状态
     */
    public static final class OpenAcctSate {

        /**
         * 未激活
         */
        public static final int UNACTIVE = 2;
        /**
         * 激活
         */
        public static final int ACTIVE = 1;
        /**
         * 冻结
         */
        public static final int FREEZE = 0;
    }

    /**
     * 开户宝收派员是否第一次登陆
     */
    public static final class OpenAcctFirst {
        public static final int YES = 1;

        public static final int NO = 2;
    }

    /**
     * 用户类型
     */
    public static final class LoginUserType {
        /**
         * 除业务员外其他用户  web页面发起的请求
         */
        public static final int OTHER_USER = 1;
        /**
         * 业务员 app发起的请求
         */
        public static final int BUSINESS_DEV = 2;
    }

    /**
     * 系统用户类型 0:系统管理员 1:运营商 2:代理商
     */
    public static final class SysUserType {

        public static final int ADMIN = 0;

        public static final int OPERATOR = 1;

        public static final int AGENT = 2;
    }

    /**
     * 业务员的初始密码为888888
     */
    public static final String OPEN_ACCT_INIT_PW = "888888";

    /**
     * 代理商状态
     */
    public static final class AgentState {
        /**
         * 未激活
         */
        public static final int UNACTIVE = 2;
        /**
         * 激活
         */
        public static final int ACTIVE = 1;
        /**
         * 冻结
         */
    }

    /**
     * 代理商申请状态 0待提交1待审核 2审核通过 3审核不通过
     */
    public static final class AgentApplyState {
        public static final int WAITING_SUBMIT = 0;
        public static final int WAITING_APPLY = 1;
        public static final int APPLY_SUCCESS = 2;
        public static final int APPLY_FAILED = 3;

    }

    /**
     * 申请单类型
     */
    public static final class ApplicationType {
        /**
         * 商户申请单
         */
        public static final int APPLICATION_MERCHANT = 1;
        /**
         * 门店申请单
         */
        public static final int APPLICATION_STORE = 2;
        /**
         * 终端申请单
         */
        public static final int APPLICATION_TERMINAL = 3;
    }

    /**
     * 终端申请单申请类型
     */
    public static final class TerminalReqType {
        /**
         * 添加
         */
        public static final int ADD = 1;
        /**
         * 换机
         */
        public static final int UPDATE = 2;
        /**
         * 撤机
         */
        public static final int DELETE = 3;
    }

    /**
     * 交易状态
     */
    public static final class TransStatus {
        /**
         * 新订单
         */
        public static final int PREPARED = 0;
        public static final String PREPARED_STR = "0";
        /**
         * 成功
         */
        public static final int SUCCESS = 1;
        public static final String SUCCESS_STR = "1";
        /**
         * 失败
         */
        public static final int FAILED = 2;
        public static final String FAILED_STR = "2";
        /**
         * 未知
         */
        public static final int UNKNOWN = 3;
        public static final String UNKNOWN_STR = "3";
        /**
         * 取消
         */
        public static final int CANCEL = 4;
        public static final String CANCEL_STR = "4";
        /**
         * 部分成功
         */
        public static final int PARTIAL_SUCCESS = 5;
        public static final String PARTIAL_SUCCESS_STR = "5";
        /**
         * 部分退货
         */
        public static final int PARTIAL_REFUND = 6;
        public static final String PARTIAL_REFUND_STR = "6";
        /**
         * 退货
         */
        public static final int REFUND = 7;
        public static final String REFUND_STR = "7";

        public static final int PRE_REVERSAL = 8;
        public static final String PRE_REVERSAL_STR = "8";

    }

    public static final class OrderStatus {
        public static final int NOT_PAY = 0;
        public static final String NOT_PAY_STR = "0";
        public static final int PAYED = 1;
        public static final String PAYED_STR = "1";
        public static final int SETTLED = 2;
        public static final String SETTLED_STR = "2";
        public static final int CANCELED = 3;
        public static final String CANCELED_STR = "3";
        public static final int PARTIALLY_REFUNED = 4;
        public static final String PARTIALLY_REFUNED_STR = "4";
        public static final int REFUNED = 5;
        public static final String REFUNED_STR = "5";
        public static final int PAY_FAILED = 6;
        public static final String PAY_FAILED_STR = "6";
        public static final int PROCESSING = 7;
        public static final String PROCESSING_STR = "7";
        public static final int UNKNOWN = 8;
        public static final String UNKNOWN_STR = "8";

    }

    public final static String Agent_Verify_Perm ="business:agent:verify";

    /**
     * 终端类型
     */
    public static final class TerminalType{
    	/** pos */
    	public static final int POS = 1;
    	/** 台牌 */
    	public static final int TP = 2;
    	/** 扫码 */
    	public static final int SM = 3;
    }

    /**
     * 图片类型
     */
    public static final class PicType{
    	/** 商户图片 */
    	public static final int MRCH = 1;
    	/** 门店图片 */
    	public static final int STORE = 2;
    }
    /**
     * 结算标志
     */
    public static final class SettleFlag{
    	/** 对公 */
    	public static final int PUBLIC = 0;
    	/** 对私 */
    	public static final int PERSONAL = 1;
    }
    /**
     * 结算类型
     */
    public static final class SettleType{
    	/** 对公 */
    	public static final int T1 = 1;
    	/** 对私 */
    	public static final int D1 = 2;
    }

    /**
     * 免密免签
     */
    public static final class SuptFbfreeFlg{
    	/** 支持 */
    	public static final int SUPPORT = 1;
    	/** 不支持 */
    	public static final int UNSUPPORT = 0;
    }
    /**
     * 产品选择
     */
    public static final class ProductChose{
    	/** 支持 */
    	public static final String CHOOSE = "Y";
    	/** 不支持 */
    	public static final String UNCHOOSE = "N";
    }

    /**
     * 进件类型
     */
    public static final class IncomeType{
    	/** 小微 */
    	public static final int MICRO = 1;
    	/** 快速 */
    	public static final int FAST = 3;
    	/** 企业 */
    	public static final int ENTERPRISE = 2;
    }

    /**
     * 国通渠道审核状态
     */
    public static final class CheckFlag{
    	/** 成功 */
    	public static final String SUCCESS = "1";
    	/** 拒绝 */
    	public static final String REFUSE = "2";
    	/** 转人工 */
    	public static final String MAN_MADE = "3";
    }
}
