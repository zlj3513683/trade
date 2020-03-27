package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import com.posppay.newpay.modules.xposp.entity.Test;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengjw
 * @since 2019-05-23
 */
public interface TestService extends IService<Test> {
    void insertAll(List<Test> tests);

}
