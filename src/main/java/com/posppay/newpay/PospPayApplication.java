package com.posppay.newpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * 系统主入口
 *
 * @author wwa
 */
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
public class PospPayApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PospPayApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PospPayApplication.class);
    }
}
