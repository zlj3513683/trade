package com.posppay.newpay.modules.sdk.cup.model.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.posppay.newpay.modules.sdk.cup.util.XmlUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 功能：
 *
 * @author 2019/7/23
 * @author zoulinjun
 */
@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Accessors(chain = true)
public class NativeTradeRequest extends PaymentRequest{
    /**
     * 银联商户号
     */
    @JSONField(name = "mch_id")
    @XmlElement(name = "mch_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String merchantId;

    /**
     * 机构号
     */
    @JSONField(name = "sign_agentno")
    @XmlElement(name = "sign_agentno")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String signAgentNo;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    @XmlElement(name = "out_trade_no")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String outTradeNo;

    /**
     * 设备号
     */
    @JSONField(name = "device_info")
    @XmlElement(name = "device_info")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String deviceInfo;

    /**
     * 商品描述
     */
    @JSONField(name = "body")
    @XmlElement(name = "body")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String body;


    /**
     * 操作员
     */
    @JSONField(name = "op_user_id")
    @XmlElement(name = "op_user_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String operatorId;

    /**
     * 门店编号
     */
    @JSONField(name = "op_shop_id")
    @XmlElement(name = "op_shop_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String shopId;

    /**
     * 附加信息
     */
    @JSONField(name = "attach")
    @XmlElement(name = "attach")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String attach;

    /**
     * 公众账号ID
     */
    @JSONField(name = "sub_appid")
    @XmlElement(name = "sub_appid")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String subAppId;

    /**
     * 总金额
     */
    @JSONField(name = "total_fee")
    @XmlElement(name = "total_fee")
    private Integer totalFee;

    /**
     * 电子发票
     */
    @JSONField(name = "need_receipt")
    @XmlElement(name = "need_receipt")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String needReceipt;

    /**
     * 终端IP
     */
    @JSONField(name = "mch_create_ip")
    @XmlElement(name = "mch_create_ip")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String machineIp;

    /**
     * 通知地址
     */
    @JSONField(name = "notify_url")
    @XmlElement(name = "notify_url")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String notifyUrl;

    /**
     * 订单生成时间
     */
    @JSONField(name = "time_start")
    @XmlElement(name = "time_start")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String timeStart;

    /**
     * 订单超时时间
     */
    @JSONField(name = "time_expire")
    @XmlElement(name = "time_expire")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String timeExpire;

    /**
     * 商品标记
     */
    @JSONField(name = "goods_tag")
    @XmlElement(name = "goods_tag")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String goodsTag;

    /**
     * 商品 ID
     */
    @JSONField(name = "product_id")
    @XmlElement(name = "product_id")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String productId;

    /**
     * 是否限制信用卡
     */
    @JSONField(name = "limit_credit_pay")
    @XmlElement(name = "limit_credit_pay")
    @XmlJavaTypeAdapter(XmlUtils.ProtectDataAdapter.class)
    private String limitCreditPay;

}
