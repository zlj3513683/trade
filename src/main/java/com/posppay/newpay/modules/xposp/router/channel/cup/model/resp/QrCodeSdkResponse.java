package com.posppay.newpay.modules.xposp.router.channel.cup.model.resp;

import lombok.Data;

/**
 * @author zengjw
 * 功能：银联C扫B返回实体
 */
@Data
public class QrCodeSdkResponse {
    /**
     * 二维码图片地址
     */
    private String codeImageUrl;
    /**
     * 二维码地址
     */
    private String codeUrl;
    /**
     *  返回码
     */
    private String returnCode;
    /**
     * 错误描述
     */
    private String messageData;
    /**
     * 交易状态
     */
    private String result;
}
