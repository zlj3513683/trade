package com.posppay.newpay.modules.sdk.cup.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 银联通讯参数
 * @author zengjw
 */
@Configurable
@Component
@Data
public class CupConfig {


    //银联地址
    @Value("${cup.url}")
    private String paymentUrl;
    @Value("${cup.notifyUrl}")
    private String notifyUrl;
    //银联真实商户
    @Value("${cup.mercId}")
    private String mercId;
    //银联真实商户
    @Value("${cup.orgNo}")
    private String orgNo;
    //银联交易真实机构秘钥
    @Value("${cup.key}")
    private String key;
    //银联商户号
    @Value("${idGen.centerId}")
    private long centerId;
    //压测开关
    @Value("${idGen.baffle}")
    private boolean baffle;
    //测试开关
    @Value("${idGen.isTest}")
    private boolean test;

}
