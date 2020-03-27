package com.posppay.newpay.modules.sdk.pingan.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置参数
 * @author MaoNing
 * @date 2019/5/14
 */
@Configurable
@Component
@Data
public class ParamConfig {


    @Value("${pingan.paymentUrl}")
    private String paymentUrl;

    @Value("${idGen.centerId}")
    private long centerId;

    @Value("${idGen.workId}")
    private long workId;

    @Value("${idGen.baffle}")
    private boolean baffle;
    @Value("${idGen.isTest}")
    private boolean test;
    @Value("${idGen.orgNo}")
    private String orgNo;
    @Value("${idGen.mercId}")
    private String mercId;
    @Value("${idGen.hMackey}")
    private String hMacKey;

}
