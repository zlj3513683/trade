package com.posppay.newpay.modules.xposp.dao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmComiService;
import com.posppay.newpay.modules.xposp.entity.UrmComi;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmComiDao;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-11
 */
@Service
public class UrmComiServiceImpl extends ServiceImpl<UrmComiDao, UrmComi> implements UrmComiService {

    @Override
    @Cacheable(value = "urmComi", unless = "#result == null ")
    public UrmComi findByMercId(String mercId) {
        Assert.notNull(mercId,"mercId  should be not null");
        UrmComi urmComi= UrmComi
                .builder()
                .mercId(mercId)
                .build();
        return baseMapper.selectOne(urmComi);
    }
}
