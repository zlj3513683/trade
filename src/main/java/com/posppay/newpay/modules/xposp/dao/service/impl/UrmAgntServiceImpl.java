package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmAgntDao;
import com.posppay.newpay.modules.xposp.dao.service.UrmAgntService;
import com.posppay.newpay.modules.xposp.entity.UrmAgnt;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
@Service
public class UrmAgntServiceImpl extends ServiceImpl<UrmAgntDao, UrmAgnt> implements UrmAgntService {

    @Override
    @Cacheable(value = "urmAgnt", unless = "#result == null ")
    public UrmAgnt findByOrgNo(String orgNo) {
        Assert.notNull(orgNo,"orgNo should not be null");
        UrmAgnt urmAgnt = new UrmAgnt();
        urmAgnt.setOrgNo(orgNo);
        return baseMapper.selectOne(urmAgnt);
    }
}
