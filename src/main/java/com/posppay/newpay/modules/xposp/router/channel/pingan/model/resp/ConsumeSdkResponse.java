package com.posppay.newpay.modules.xposp.router.channel.pingan.model.resp;

import lombok.Data;

/**
 * @author zengjw
 * 功能：平安扫码返回实体
 */
@Data
public class ConsumeSdkResponse {
    /**
     * 结果
     */
    private String result;
    /**
     * 错误码
     */
    private String returnCode;
    /**
     * 错误描述
     */
    private String messageData;
}
