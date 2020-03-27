package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.ChannelInfo;

/**
 * <p>
 * 渠道信息表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
public interface ChannelInfoService extends IService<ChannelInfo> {


    ChannelInfo findByChnCode(String chnCode);
}
