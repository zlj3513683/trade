package com.posppay.newpay.modules.sdk.starpos.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Configurable
@Component
@Data
public class StartPosParam {
    //验签秘钥
    @Value("${gt.starpos.key}")
    private String key;
    //url
    @Value("${gt.starpos.url}")
    private String url;
    //商户号
    @Value("${gt.starpos.mercId}")
    private String mercId;
    //商户号
    @Value("${gt.starpos.trmnlNo}")
    private String trmnlNo;

}
