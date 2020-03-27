package com.posppay.newpay.modules.xposp.dao.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.TmsSnnoService;
import com.posppay.newpay.modules.xposp.entity.TmsSnno;
import com.posppay.newpay.modules.xposp.dao.mapper.TmsSnnoDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-11
 */
@Service
public class TmsSnnoServiceImpl extends ServiceImpl<TmsSnnoDao, TmsSnno> implements TmsSnnoService {

    @Override
    public TmsSnno findBySnNo(String snNo) {
        TmsSnno tmsSnno = TmsSnno
                .builder()
                .snNo(snNo)
                .build();
        return baseMapper.selectOne(tmsSnno);
    }
}
