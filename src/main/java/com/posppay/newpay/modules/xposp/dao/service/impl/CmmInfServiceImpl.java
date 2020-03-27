package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.CmmInfService;
import com.posppay.newpay.modules.xposp.entity.CmmInf;
import com.posppay.newpay.modules.xposp.router.channel.MrchAndTrmnl;
import com.posppay.newpay.modules.xposp.dao.mapper.CmmInfDao;
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
public class CmmInfServiceImpl extends ServiceImpl<CmmInfDao, CmmInf> implements CmmInfService {
    @Override
    public CmmInf findByMrchAndTrmNo(MrchAndTrmnl mrchAndTrmnl) {
        CmmInf cmmInf = CmmInf
                .builder()
                .corgNo(mrchAndTrmnl.getCorgNo())
                .corgMercId(mrchAndTrmnl.getCorgMercId())
                .corgTrmNo(mrchAndTrmnl.getCorgTrmNo())
                .build();
        return baseMapper.selectOne(cmmInf);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateByMrchAndTrmNo(CmmInf cmmInf) {
//        System.out.println(cmmInf);
//        EntityWrapper<CmmInf> wrapper = new EntityWrapper();
////        wrapper.eq("CORG_NO", StarPosProperty.mrchAndTrmnl.getCorgNo())
////                .eq("CORG_TRM_NO", StarPosProperty.mrchAndTrmnl.getCorgTrmNo());
//        baseMapper.update(cmmInf,wrapper);
    }
}
