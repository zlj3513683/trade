package com.posppay.newpay.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * mybatis-plus配置
 *
 * @author wwa
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    /**
     * SQL执行效率插件
     */
//    @Bean
//    @Profile({"dev","inte"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
//        performanceInterceptor.setMaxTime(10000);
//        /*<!--SQL是否格式化 默认false-->*/
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
}
