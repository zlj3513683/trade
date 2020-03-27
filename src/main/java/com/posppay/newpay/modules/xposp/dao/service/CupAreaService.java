package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.CupArea;

/**
 * <p>
 * 银联地区码 服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-06-12
 */
public interface CupAreaService extends IService<CupArea> {
    /**
     * 根据地区编码来查找转换后的平安地区编码
     * @param areaCode
     * @return
     */
   CupArea findAreaCode(String areaCode);

}
