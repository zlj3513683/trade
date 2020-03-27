package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmMinf;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-20
 */
public interface UrmMinfService extends IService<UrmMinf> {
    /**
     * 功能： 根据商户号获取商户信息
     * @param mercId
     * @return
     */
    UrmMinf findBymercId(String mercId);

}
