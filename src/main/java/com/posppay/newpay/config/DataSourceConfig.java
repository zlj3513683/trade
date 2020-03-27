package com.posppay.newpay.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置多数据源
 *
 * @author wwa
 */
//@Configuration
public class DataSourceConfig {

//    @Bean
//    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }

}

