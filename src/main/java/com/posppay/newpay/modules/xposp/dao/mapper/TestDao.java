package com.posppay.newpay.modules.xposp.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.posppay.newpay.modules.xposp.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengjw
 * @since 2019-05-23
 */
@Mapper
public interface TestDao extends BaseMapper<Test> {
    void insertAll(@Param("tests") List<Test> tests);

}
