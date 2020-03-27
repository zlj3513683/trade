package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.MercTransChnDao;
import com.posppay.newpay.modules.xposp.dao.service.MercTransChnService;
import com.posppay.newpay.modules.xposp.entity.MercTransChn;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
@Service
public class MercTransChnServiceImpl extends ServiceImpl<MercTransChnDao, MercTransChn> implements MercTransChnService {
    /**
     * 功能： 根据商户号来获取指定的渠道
     * @param mercId
     * @return
     */
    @Override
    @Cacheable(value = "mercTransChn", unless = "#result == null ")
    public MercTransChn findByMercId(String mercId) {
        Assert.notNull(mercId,"mercId  should be not null");
        MercTransChn mercTransChn = MercTransChn
                .builder()
                .mercId(mercId)
                .build();
        return baseMapper.selectOne(mercTransChn);
    }
}
