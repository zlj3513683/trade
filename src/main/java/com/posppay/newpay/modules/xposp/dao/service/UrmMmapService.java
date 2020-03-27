package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmMmap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
public interface UrmMmapService extends IService<UrmMmap> {
    /**
     * 根据系统商户号和编号来唯一确定转换后的商户号
     */
    UrmMmap findByMrcId(String mrcId,String prdId);

}
