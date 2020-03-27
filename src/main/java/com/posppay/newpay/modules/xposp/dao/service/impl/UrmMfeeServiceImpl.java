package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmMfeeService;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmMfeeDao;
import com.posppay.newpay.modules.xposp.entity.UrmMfee;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 费率表 mybatisplus业务层
 * </p>
 *
 * @author zengjw
 * @since 2019-05-21
 */
@Service
public class UrmMfeeServiceImpl extends ServiceImpl<UrmMfeeDao, UrmMfee> implements UrmMfeeService {

    /**
     * 功能： 根据商户号门店号和产品编号来获取费率
     * @param mercId
     * @param stoeId
     * @param prdId
     * @return
     */
    @Override
    @Cacheable(value = "urmMfee", unless = "#result == null ")
    public UrmMfee findByMrchAndStoe(String mercId, String stoeId, String prdId) {
        Assert.notNull(mercId,"mercId  should be not null");
        Assert.notNull(stoeId,"stoeId  should be not null");
        Assert.notNull(prdId,"prdId  should be not null");
        UrmMfee urmMfee = UrmMfee
                .builder()
                .mercId(mercId)
                .stoeId(stoeId)
                .prdId(prdId)
                .build();
        return baseMapper.selectOne(urmMfee);
    }
}
