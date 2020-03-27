package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmStoe;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-17
 */
public interface UrmStoeService extends IService<UrmStoe> {
    /**
     * 根据门店号来获取门店信息
     * @param storeNo
     * @return
     */
    List<UrmStoe> findByStoreNo(String mercId, String storeNo);

}
