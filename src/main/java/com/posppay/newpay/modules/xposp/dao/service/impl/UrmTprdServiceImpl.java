package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmTprdService;
import com.posppay.newpay.modules.xposp.entity.UrmTprd;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmTprdDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
@Service
public class UrmTprdServiceImpl extends ServiceImpl<UrmTprdDao, UrmTprd> implements UrmTprdService {

    @Override
    public UrmTprd findByTrmnlNo(String trmnlNo, String prdId) {
        UrmTprd urmTprd = UrmTprd
                .builder()
                .trmNo(trmnlNo)
                .prdId(prdId)
                .build();
        return baseMapper.selectOne(urmTprd);
    }
}
