package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.TmsPos;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
public interface TmsPosService extends IService<TmsPos> {

    /**
     * 根据设备sn号查找
     * @param snNo
     * @return
     */
    TmsPos findBySnNo(String snNo);

    /**
     * 根据商户信息获取
     * @param mercId
     * @param stoeNo
     * @return
     */
    TmsPos findBymercId(String mercId,String stoeNo);

}
