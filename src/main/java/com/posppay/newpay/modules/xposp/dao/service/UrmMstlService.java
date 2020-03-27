package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmMstl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-20
 */
public interface UrmMstlService extends IService<UrmMstl> {
    /**
     * 功能： 根据商户号门店号和产品好来查找
     * @param mercId
     * @param stoeId
     * @return
     */
   UrmMstl findByMrchAndStoeNo(String mercId,String stoeId,String prdId);

}
