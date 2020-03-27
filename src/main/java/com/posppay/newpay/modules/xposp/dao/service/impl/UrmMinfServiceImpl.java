package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmMinfService;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmMinfDao;
import com.posppay.newpay.modules.xposp.entity.UrmMinf;
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
public class UrmMinfServiceImpl extends ServiceImpl<UrmMinfDao, UrmMinf> implements UrmMinfService {
    /**
     * 根据商户号来获取系统商户信息
     * @param mercId
     * @return
     */
    @Override
    @Cacheable(value = "merchant", unless = "#result == null ")
    public UrmMinf findBymercId(String mercId) {
        Assert.notNull(mercId,"mercId should be not null");
        UrmMinf urmMinf = UrmMinf
                .builder()
                .mercId(mercId)
                .build();
        return baseMapper.selectOne(urmMinf);

    }
}
