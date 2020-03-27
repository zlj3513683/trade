package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.ChannelInfoDao;
import com.posppay.newpay.modules.xposp.dao.service.ChannelInfoService;
import com.posppay.newpay.modules.xposp.entity.ChannelInfo;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 渠道信息表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
@Service
public class ChannelInfoServiceImpl extends ServiceImpl<ChannelInfoDao, ChannelInfo> implements ChannelInfoService {

    @Override
    @Cacheable(value = "channelInfo", unless = "#result == null ")
    public ChannelInfo findByChnCode(String chnCode) {
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setChnCode(chnCode);
        return baseMapper.selectOne(channelInfo);
    }
}
