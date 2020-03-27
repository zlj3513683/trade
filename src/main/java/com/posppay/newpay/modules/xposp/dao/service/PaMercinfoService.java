package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.PaMercinfo;

/**
 * <p>
 * 平安商户信息表 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-13
 */
public interface PaMercinfoService extends IService<PaMercinfo> {

    PaMercinfo findBymercId(String mercId);

}
