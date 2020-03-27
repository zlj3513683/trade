package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmPrdt;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-20
 */
public interface UrmPrdtService extends IService<UrmPrdt> {
    UrmPrdt findByMrchAndStore(String mercId,String stoeId,String prdId);

}
