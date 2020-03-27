package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmAgtfeeDao;
import com.posppay.newpay.modules.xposp.dao.service.UrmAgtfeeService;
import com.posppay.newpay.modules.xposp.entity.UrmAgtfee;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
@Service
public class UrmAgtfeeServiceImpl extends ServiceImpl<UrmAgtfeeDao, UrmAgtfee> implements UrmAgtfeeService {

    @Override
    @Cacheable(value = "urmAgtFee", unless = "#result == null ")
    public UrmAgtfee findByAgtMercId(String agtMercId) {
        Assert.notNull(agtMercId,"agtMercId should not be null");
        UrmAgtfee urmAgtfee = new UrmAgtfee();
        urmAgtfee.setAgtMercId(agtMercId);
        return baseMapper.selectOne(urmAgtfee);
    }
}
