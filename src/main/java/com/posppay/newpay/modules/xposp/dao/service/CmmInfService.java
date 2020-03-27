package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.CmmInf;
import com.posppay.newpay.modules.xposp.router.channel.MrchAndTrmnl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
public interface CmmInfService extends IService<CmmInf> {
    /**
     * 查询机构工作秘钥相关信息
     *
     */
    CmmInf findByMrchAndTrmNo(MrchAndTrmnl mrchAndTrmnl);
    /**
     * 更新机构工作秘钥相关信息
     */
    void updateByMrchAndTrmNo(CmmInf cmmInf);

}
