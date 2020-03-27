package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmComi;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-11
 */
public interface UrmComiService extends IService<UrmComi> {
    /**
     * 根据系统商户id来查找
     * @param mercId
     * @return
     */
    UrmComi findByMercId(String mercId);

}
