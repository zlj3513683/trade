package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.TmsSnno;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-11
 */
public interface TmsSnnoService extends IService<TmsSnno> {

    /**
     * 根据snNo来查找
     * @param snNo
     * @return
     */
    TmsSnno findBySnNo(String snNo);
}
