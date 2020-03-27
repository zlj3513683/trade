package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.CupMerchantService;
import com.posppay.newpay.modules.xposp.dao.mapper.CupMerchantDao;
import com.posppay.newpay.modules.xposp.entity.CupMerchant;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-17
 */
@Service
public class CupMerchantServiceImpl extends ServiceImpl<CupMerchantDao, CupMerchant> implements CupMerchantService {

    @Override
    public CupMerchant findByMercIdAndStoreNo(String mercId, String storeNo) {
        CupMerchant cupMerchant = CupMerchant
                .builder()
                .merchantId(mercId)
                .stoeId(storeNo)
                .build();
       return baseMapper.selectOne(cupMerchant);
    }
}
