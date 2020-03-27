package com.posppay.newpay.modules.xposp.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * 序列号Dao层
 *
 * @author zengjw
 * @since 2019-05-14
 */
@Mapper
public interface SequenceDao extends BaseMapper<java.lang.Long> {

    Long findSequence();

}
