package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmTprd;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
public interface UrmTprdService extends IService<UrmTprd> {
    /**
     * 根据系统终端号和编号来确定唯一的一个渠道终端号
     */
    UrmTprd findByTrmnlNo(String trmnlNo,String prdId);
}
