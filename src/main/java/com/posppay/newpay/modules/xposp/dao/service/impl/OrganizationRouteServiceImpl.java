package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.common.utils.StringUtils;
import com.posppay.newpay.modules.xposp.dao.mapper.OrganizationRouteDao;
import com.posppay.newpay.modules.xposp.dao.service.OrganizationRouteService;
import com.posppay.newpay.modules.xposp.entity.OrganizationRoute;
import org.apache.shiro.util.Assert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平安机构秘钥表 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
@Service
public class OrganizationRouteServiceImpl extends ServiceImpl<OrganizationRouteDao, OrganizationRoute> implements OrganizationRouteService {
    @Override
    @Cacheable(value = "organizationRoute", unless = "#result == null ")
    public OrganizationRoute findByAreaCode(String provCode, String cityCode) {
        Assert.notNull(provCode,"provCode should be not null");
        OrganizationRoute organizationRoute = new OrganizationRoute();
        organizationRoute.setMerchantProvinceId(provCode);
        if (StringUtils.isNotBlank(cityCode)){
            organizationRoute.setMerchantCityId(cityCode);
        }
        return baseMapper.selectOne(organizationRoute);
    }

    @Override
    @Cacheable(value = "organizationByProv", unless = "#result == null ")
    public OrganizationRoute findByProCode(String provCode) {
        Assert.notNull(provCode,"provCode should be not null");
        EntityWrapper<OrganizationRoute>entityWrapper = new EntityWrapper<OrganizationRoute>();
        entityWrapper.eq("MERCHANT_PROVINCE_ID",provCode);
        entityWrapper.isNull("MERCHANT_CITY_ID");
        List<OrganizationRoute> organizationRoutes = baseMapper.selectList(entityWrapper);
        if (null!=organizationRoutes&&0!=organizationRoutes.size()){
           return organizationRoutes.get(0);
        }else {
            return null;
        }
    }
}
