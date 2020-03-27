package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmMfee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-21
 */
public interface UrmMfeeService extends IService<UrmMfee> {
    /**
     * 功能： 根据商户门店费率
     * @param mercId
     * @param stoeId
     * @param prdId
     * @return
     */
    UrmMfee findByMrchAndStoe(String mercId,String stoeId,String prdId);


}
