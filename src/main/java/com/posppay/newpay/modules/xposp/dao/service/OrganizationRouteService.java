package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.OrganizationRoute;

/**
 * <p>
 * 平安机构秘钥表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
public interface OrganizationRouteService extends IService<OrganizationRoute> {

   OrganizationRoute findByAreaCode(String provCode,String cityCode);

   OrganizationRoute findByProCode(String provCode);

}
