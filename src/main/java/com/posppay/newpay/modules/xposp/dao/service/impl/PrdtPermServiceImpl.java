package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.PrdtPermService;
import com.posppay.newpay.modules.xposp.dao.mapper.PrdtPermDao;
import com.posppay.newpay.modules.xposp.entity.PrdtPerm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-18
 */
@Service
public class PrdtPermServiceImpl extends ServiceImpl<PrdtPermDao, PrdtPerm> implements PrdtPermService {

    @Override
    public List<PrdtPerm> queryByMrchAndTrmnlNo(String mrchNo, String storeNo, String prdId) {
//        PrdtPerm prdtPerm = PrdtPerm
//                .builder()
//                .mercId(mrchNo)
//                .stoeId(storeNo)
//                .prdId(prdId)
//                .build();
        EntityWrapper<PrdtPerm> entityWrapper = new EntityWrapper<PrdtPerm>();
        entityWrapper.eq("MERC_ID",mrchNo)
                .eq("STOE_ID",storeNo)
                .eq("PRD_ID",prdId);
       return baseMapper.selectList(entityWrapper);
    }
}
