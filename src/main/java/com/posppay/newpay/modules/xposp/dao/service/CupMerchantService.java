package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.CupMerchant;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-17
 */
public interface CupMerchantService extends IService<CupMerchant> {
    /**
     * 根据系统商户号和门店号来获取银联认可的商户
     * @param mercId
     * @param storeNo
     * @return
     */
    CupMerchant findByMercIdAndStoreNo(String mercId,String storeNo);

}
