package com.posppay.newpay.modules.xposp.transfer.model.resp;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 功能：B扫C消费返回实体
 * @author zengjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ConsumeResponse {
    /**
     * 交易状态
     */
    private String status;
    /**
     * 交易凭证号
     */
    private String payNo;
    /**
     * 下游订单号
     */
    private String outOrderNo;
    /**
     * 平台商户号
     */
    private String mrchNo;
    /**
     * 平台商户名称
     */
    private String mrchName;
    /**
     * 货币代码CNy
     */
    private String currency;

    /**
     * 金额
     */
    private String amount;
    /**
     * 交易类型
     */
    private String transType;
    /**
     * 交易类型中文名称
     */
    private String transTypeNm;
    /**
     * 商品类型
     */
    private String goodsTag;
    /**
     * 商品信息
     */
    private String goodsDetail;
    /**
     * 附加信息
     */
    private String attach;

    /**
     * 商品描述
     */
    private String body;
    /**
     * 交易错误码
     */
    private String transCode;
    /**
     * 错误信息
     */
    private String transMsg;
    /**
     * 原始平台订单号
     */
    private String originPayNo;
    /**
     * 交易子类型
     */
    private String subTransType;
    /**
     * 微信支付宝订单号
     */
    private String outTranstionId;

    /**
     * 借贷记标识
     */
    private String bankType;






}
