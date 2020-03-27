package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmPrdtService;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmPrdtDao;
import com.posppay.newpay.modules.xposp.entity.UrmPrdt;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-20
 */
@Service
public class UrmPrdtServiceImpl extends ServiceImpl<UrmPrdtDao, UrmPrdt> implements UrmPrdtService {

    @Override
    @Cacheable(value = "urmPrdt", unless = "#result == null ")
    public UrmPrdt findByMrchAndStore(String mercId, String stoeId, String prdId) {
        Assert.notNull(mercId,"mercId should be not null");
        Assert.notNull(stoeId,"stoeId should be not null");
        Assert.notNull(prdId,"prdId should be not null");
        UrmPrdt urmPrdt = UrmPrdt
                .builder()
                .mercId(mercId)
                .stoeId(stoeId)
                .prdId(prdId)
                .build();

        return baseMapper.selectOne(urmPrdt);
    }
}
