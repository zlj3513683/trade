package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.ChannelScanSupport;

/**
 * <p>
 * 扫码渠道支持表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-07-23
 */
public interface ChannelScanSupportService extends IService<ChannelScanSupport> {
    /**
     * 根据渠道号和商户类型获取渠道支持表记录
     * @param chnCode
     * @param mercType
     * @return
     */
   ChannelScanSupport findBychnCode(String chnCode,String mercType);

}
