package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.PrdtPerm;

import java.util.List;

/**
 * <p>
 *  产品权限service
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
public interface PrdtPermService extends IService<PrdtPerm> {
    /**
     * 根据商户号门店号和产品好查询权限信息
     * @param MrchNo
     * @param storeNo
     * @param prdId
     * @return
     */
    List<PrdtPerm> queryByMrchAndTrmnlNo(String MrchNo, String storeNo, String prdId);

}
