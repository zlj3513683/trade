package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.mapper.CupAreaDao;
import com.posppay.newpay.modules.xposp.dao.service.CupAreaService;
import com.posppay.newpay.modules.xposp.entity.CupArea;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银联地区码 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
@Service
public class CupAreaServiceImpl extends ServiceImpl<CupAreaDao, CupArea> implements CupAreaService {

    /**
     * 根据地区编码来查找转换后的平安地区编码
     *
     * @param areaCode
     * @return
     */
    @Override
    @Cacheable(value = "area", unless = "#result == null ")
    public CupArea findAreaCode(String areaCode) {
        Assert.notNull(areaCode,"areaCode should be not null");
        CupArea cupArea = new CupArea();
        cupArea.setCupAreaId(areaCode);
        return baseMapper.selectOne(cupArea);
    }
}
