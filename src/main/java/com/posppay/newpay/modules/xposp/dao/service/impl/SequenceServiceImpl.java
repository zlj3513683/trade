package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.SequenceService;
import com.posppay.newpay.modules.xposp.dao.mapper.SequenceDao;
import org.springframework.stereotype.Service;

/**
 *
 * 序列号service层
 *
 * @author zengjw
 * @since 2019-05-14
 */
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceDao, java.lang.Long> implements SequenceService {


    @Override
    public Long findSequence() {
        return baseMapper.findSequence();
    }
}
