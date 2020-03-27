package com.posppay.newpay.modules.xposp.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.posppay.newpay.modules.xposp.dao.service.TestService;
import com.posppay.newpay.modules.xposp.dao.mapper.TestDao;
import com.posppay.newpay.modules.xposp.entity.Test;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-23
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestDao, Test> implements TestService {

    @Override
    public void insertAll(List<Test> tests) {
        baseMapper.insertAll(tests);
    }
}
