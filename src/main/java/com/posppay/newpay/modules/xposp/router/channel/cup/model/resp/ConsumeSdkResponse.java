package com.posppay.newpay.modules.xposp.router.channel.cup.model.resp;

import lombok.Data;

/**
 * @author zengjw
 * 功能：银联扫码返回实体
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
