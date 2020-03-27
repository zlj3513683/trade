package com.posppay.newpay.modules.xposp.dao.service;

import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * 序列号Dao层
 *
 * @author zengjw
 * @since 2019-05-14
 */
public interface SequenceService extends IService<java.lang.Long> {

    Long findSequence();

}
