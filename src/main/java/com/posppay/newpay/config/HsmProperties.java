package com.posppay.newpay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YangCH
 * create on 2019/2/22
 */
@Component
@ConfigurationProperties(prefix = "hsm")
@Data
public class HsmProperties {
    private String ip;
    private String port;
    private String zmk;
}
