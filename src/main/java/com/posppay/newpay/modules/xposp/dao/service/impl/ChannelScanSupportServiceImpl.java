package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.ChannelScanSupportDao;
import com.posppay.newpay.modules.xposp.dao.service.ChannelScanSupportService;
import com.posppay.newpay.modules.xposp.entity.ChannelScanSupport;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 扫码渠道支持表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-07-23
 */
@Service
public class ChannelScanSupportServiceImpl extends ServiceImpl<ChannelScanSupportDao, ChannelScanSupport> implements ChannelScanSupportService {

    /**
     * 根据渠道号获取渠道支持表记录
     *
     * @param chnCode
     * @return
     */
    @Override
    @Cacheable(value = "channelsupport", unless = "#result == null ")
    public ChannelScanSupport findBychnCode(String chnCode,String mercType) {
        Assert.notNull(chnCode,"chnCode should not be null");
        Assert.notNull(mercType,"mercType should not be null");
        ChannelScanSupport channelScanSupport = new ChannelScanSupport();
        channelScanSupport.setChnCode(chnCode);
        channelScanSupport.setMercType(mercType);
        return baseMapper.selectOne(channelScanSupport);

    }
}
