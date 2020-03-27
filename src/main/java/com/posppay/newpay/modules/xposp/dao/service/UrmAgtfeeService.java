package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.UrmAgtfee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-27
 */
public interface UrmAgtfeeService extends IService<UrmAgtfee> {
   UrmAgtfee findByAgtMercId(String agtMercId);
}
