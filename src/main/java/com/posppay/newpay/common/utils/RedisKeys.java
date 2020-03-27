package com.posppay.newpay.common.utils;

/**
 * Redis所有Keys
 *
 * @author wwa
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getSysCaptchaKey(String key){
        return "sys:captcha:" + key;
    }

}
