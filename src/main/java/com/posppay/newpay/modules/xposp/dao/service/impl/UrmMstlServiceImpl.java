package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmMstlService;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmMstlDao;
import com.posppay.newpay.modules.xposp.entity.UrmMstl;
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
public class UrmMstlServiceImpl extends ServiceImpl<UrmMstlDao, UrmMstl> implements UrmMstlService {

    /**
     * 功能： 根据商户号和产品好来获取结算信息
     * @param mercId
     * @param stoeId
     * @param prdId
     * @return
     */
    @Override
    @Cacheable(value = "urmMstl", unless = "#result == null ")
    public UrmMstl findByMrchAndStoeNo(String mercId, String stoeId, String prdId) {
        Assert.notNull(mercId,"mercId should be not null");
        Assert.notNull(stoeId,"stoeId should be not null");
        Assert.notNull(prdId,"prdId should be not null");
        UrmMstl urmMstl = UrmMstl
                .builder()
                .mercId(mercId)
                .stoeId(stoeId)
                .prdId(prdId)
                .build();
        return baseMapper.selectOne(urmMstl);
    }
}
