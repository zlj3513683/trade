package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.service.UrmStoeService;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmStoeDao;
import com.posppay.newpay.modules.xposp.entity.UrmStoe;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-17
 */
@Service
public class UrmStoeServiceImpl extends ServiceImpl<UrmStoeDao, UrmStoe> implements UrmStoeService {
    /**
     * 根据门店号来获取门店信息
     * @param storeNo
     * @return
     */
    @Override
    @Cacheable(value = "store", unless = "#result == null ")
    public List<UrmStoe> findByStoreNo(String mercId, String storeNo) {
        Assert.notNull(mercId,"mercId should be not null");
        EntityWrapper<UrmStoe> entityWrapper = new EntityWrapper<UrmStoe>();
        entityWrapper.eq("MERC_ID",mercId);
        if (StringUtils.isNotBlank(storeNo)){
            entityWrapper.eq("STOE_ID",storeNo);
        }
        return baseMapper.selectList(entityWrapper);
    }
}
