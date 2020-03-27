package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.PaMercinfoDao;
import com.posppay.newpay.modules.xposp.dao.service.PaMercinfoService;
import com.posppay.newpay.modules.xposp.entity.PaMercinfo;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平安商户信息表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
@Service
public class PaMercinfoServiceImpl extends ServiceImpl<PaMercinfoDao, PaMercinfo> implements PaMercinfoService {
    @Override
    @Cacheable(value = "paMercInfo", unless = "#result == null ")
    public PaMercinfo findBymercId(String mercId) {
        Assert.notNull(mercId,"mercId should be not null");
        PaMercinfo paMercinfo = new PaMercinfo();
        paMercinfo.setMercId(mercId);
        return baseMapper.selectOne(paMercinfo);
    }
}
