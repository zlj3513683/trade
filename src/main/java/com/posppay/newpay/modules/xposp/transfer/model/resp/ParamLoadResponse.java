package com.posppay.newpay.modules.xposp.transfer.model.resp;

import com.posppay.newpay.modules.model.ProxyResponse;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 功能：参数同步返回
 * @author zengjw终端实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ParamLoadResponse extends ProxyResponse {
    /**
     * TODO 不知道什么意思
     */
    private String binflg;
    /**
     * 主秘钥索引
     */
    private String keyIndex;
    /**
     * 商户名
     */
    private String merNam;
    /**
     * 三个交易电话号码之一
     */
    private String phNo1;
    /**
     * 三个交易电话号码之二
     */
    private String phNo2;
    /**
     * 三个交易电话号码之三
     */
    private String phNo3;
    /**
     * 一个管理电话号码
     */
    private String phNoMng;
    /**
     * 交易重发次数
     */
    private String retranNm;
    /**
     * 重试次数
     */
    private String retryNum;
    /**
     * 扫码小票商户名称
     */
    private String scanMerNam;
    /**
     * scanMercId
     */
    private String scanMercId;
    /**
     * 增值服务
     */
    private String servList;
    /**
     * 是否自动签退
     */
    private String supAutOut;
    /**
     * 是否支持手工输入卡号
     */
    private String supInputCard;
    /**
     * 是否支持小费
     */
    private String supTip;
    /**
     * 超时时间
     */
    private String timeOut;
    /**
     * 小费百分比
     */
    private String tipPer;

    /**
     * POS终端应用类型
     */
    private String trmTyp;
    /**
     * 支持的交易类型
     */
    private String txnList;







}
