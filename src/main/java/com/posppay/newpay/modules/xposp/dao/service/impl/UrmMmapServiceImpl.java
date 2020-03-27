package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.UrmMmapService;
import com.posppay.newpay.modules.xposp.entity.UrmMmap;
import com.posppay.newpay.modules.xposp.dao.mapper.UrmMmapDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-14
 */
@Service
public class UrmMmapServiceImpl extends ServiceImpl<UrmMmapDao, UrmMmap> implements UrmMmapService {

    @Override
    public UrmMmap findByMrcId(String mercId, String prdId) {
        UrmMmap urmMmap = UrmMmap
                .builder()
                .mercId(mercId)
                .prdId(prdId)
                .build();
        return baseMapper.selectOne(urmMmap);

    }
}
