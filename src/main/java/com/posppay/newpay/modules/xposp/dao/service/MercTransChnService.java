package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.MercTransChn;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
public interface MercTransChnService extends IService<MercTransChn> {
    /**
     * 功能： 根据商户号来获取对应渠道
     * @param mercId
     * @return
     */
    MercTransChn findByMercId(String mercId);

}
