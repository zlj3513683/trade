package com.posppay.newpay.modules.sdk.pingan.service;

import com.posppay.newpay.modules.sdk.pingan.model.resp.ServiceMessage;

/**
 * 回调通知服务
 * @author MaoNing
 * @date 2019/5/14
 */
public interface NoticeService {

    /**
     * C扫B平安通知处理
     * @param data
     * @return
     */
    ServiceMessage scanBusNotice(String data);
}
