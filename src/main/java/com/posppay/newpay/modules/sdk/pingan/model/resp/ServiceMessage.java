package com.posppay.newpay.modules.sdk.pingan.model.resp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义服务层响应
 * @author MaoNing
 * @date 2019/4/16
 */
@Slf4j
@Data
public class ServiceMessage {

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public ServiceMessage() {
    }

    public ServiceMessage(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ServiceMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
