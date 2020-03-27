package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.TmsPosService;
import com.posppay.newpay.modules.xposp.entity.TmsPos;
import com.posppay.newpay.modules.xposp.dao.mapper.TmsPosDao;
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
public class TmsPosServiceImpl extends ServiceImpl<TmsPosDao, TmsPos> implements TmsPosService {

    /**
     * 根据设备sn查找
     *
     * @param snNo
     * @return
     */
    @Override
    public TmsPos findBySnNo(String snNo) {
        TmsPos tmsPos = TmsPos
                .builder()
                .snNo(snNo)
                .build();
        return baseMapper.selectOne(tmsPos);
    }

    /**
     * 根据商户信息获取
     *
     * @param mercId
     * @param stoeNo
     * @return
     */
    @Override
    public TmsPos findBymercId(String mercId, String stoeNo) {
        TmsPos tmsPos = TmsPos
                .builder()
                .mercId(mercId)
                .stoeId(stoeNo)
                .build();
        return baseMapper.selectOne(tmsPos);
    }
}
